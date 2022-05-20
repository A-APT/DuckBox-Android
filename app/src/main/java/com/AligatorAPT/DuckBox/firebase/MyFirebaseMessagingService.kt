package com.AligatorAPT.DuckBox.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.view.activity.NavigationActivity
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

        showNotification("id", "타이틀", 0)

        if (p0.data.isNotEmpty()) {
            showNotification(
                p0.data["id"]!!, // group or vote or survey id
                p0.data["title"]!!, // group name
                p0.data["type"]!!.toInt() // group(0) or vote(1) or survey(2)
            )
        }
    }

    private fun showNotification(id: String, title: String, type: Int) {
        val intent = Intent(this, NavigationActivity::class.java)
        val bundle = Bundle()
        bundle.putBoolean("isAlarm", true)
        intent.putExtras(bundle)
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val channel_id = "channel"

        val message: String = when(type) {
            0 -> "그룹에 가입했습니다."
            1 -> "투표가 생성되었습니다."
            2 -> "설문이 생성되었습니다."
            else -> "DUCK BOX"
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var builder = NotificationCompat.Builder(applicationContext, channel_id)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.duck_main)
            .setSound(uri)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(message)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channel_id, "duckbox", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.setSound(uri, null)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())

        // TODO store id, type
    }
}
