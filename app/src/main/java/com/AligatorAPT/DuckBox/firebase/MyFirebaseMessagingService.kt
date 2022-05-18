package com.AligatorAPT.DuckBox.firebase

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.AligatorAPT.DuckBox.MainActivity
import com.AligatorAPT.DuckBox.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        //클라우드 서버에 등록되었을 때 호출되며, p0가 고유 키
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        //수신한 메세지를 처리 (사용자에게 알림을 보내기)
        Log.e("DATA", p0.data.toString())
        Log.e("FROM", p0.from.toString())
        Log.e("NOTIFICATION", p0.notification.toString())
        Log.e("RAW", p0.rawData.toString())

        if (p0.data.isNotEmpty()) {
            Log.e("MESSAGE", p0.data["title"].toString())
            showNotification(
                p0.data["title"],
                p0.data["message"]
            )
        }

        if (p0.notification != null) {
            Log.e("MESSAGE", p0.notification!!.title.toString())
            showNotification(
                p0.notification!!.title,
                p0.notification!!.body
            )
        }
    }

    @SuppressLint("RemoteViewLayout")
    private fun getCustomDesign(title: String?, message: String?): RemoteViews? {
        val remoteViews = RemoteViews(applicationContext.packageName, R.layout.notification)
        remoteViews.setTextViewText(R.id.noti_title, title)
        remoteViews.setTextViewText(R.id.noti_message, message)
        return remoteViews
    }

    private fun showNotification(title: String?, message: String?) {
        val intent = Intent(this, MainActivity::class.java)
        val channel_id = "channel"

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var builder = NotificationCompat.Builder(applicationContext, channel_id)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSound(uri)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setContent(getCustomDesign(title, message));
        } else {
            builder.setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channel_id, "web_app", NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.setSound(uri, null)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())
    }
}
