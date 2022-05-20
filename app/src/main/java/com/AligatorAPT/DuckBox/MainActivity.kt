package com.AligatorAPT.DuckBox

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.AligatorAPT.DuckBox.databinding.ActivityMainBinding
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity
import com.AligatorAPT.DuckBox.view.activity.LoginActivity
import com.AligatorAPT.DuckBox.view.activity.NavigationActivity
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity
import com.AligatorAPT.DuckBox.viewmodel.GroupViewModel
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Provider
import java.security.Security

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val model: GroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBouncyCastle()

        init()
    }

    private fun init() {
        binding.apply {
            //회원가입 화면 전환
            signUpButton.setOnClickListener {
                val intent = Intent(this@MainActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
            //네비게이션 화면 전환
            navigationButton.setOnClickListener {
                val intent = Intent(this@MainActivity, NavigationActivity::class.java)
                startActivity(intent)
            }

            createVoteButton.setOnClickListener {
                val intent = Intent(this@MainActivity, CreateVoteActivity::class.java)
                startActivity(intent)
            }

            LoginButton.setOnClickListener {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }

            testFCM.setOnClickListener {
                model.testNotification(object : ApiCallback {
                    override fun apiCallback(flag: Boolean) {
                        if(flag){
                            Toast.makeText(this@MainActivity, "보내짐!", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }

            alarm.setOnClickListener {
                val intent = Intent(this@MainActivity, NavigationActivity::class.java)
                val channel_id = "channel"

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                val pendingIntent = PendingIntent.getActivity(this@MainActivity, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                var builder = NotificationCompat.Builder(applicationContext, channel_id)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.duck_main)
                    .setSound(uri)
                    .setAutoCancel(true)
                    .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                    .setOnlyAlertOnce(true)
                    .setContentIntent(pendingIntent)
                    .setContentTitle("알림!!")
                    .setContentText("알림이 왔어요.")

                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val notificationChannel =
                        NotificationChannel(channel_id, "web_app", NotificationManager.IMPORTANCE_HIGH)
                    notificationChannel.setSound(uri, null)
                    notificationManager.createNotificationChannel(notificationChannel)
                }

                notificationManager.notify(0, builder.build())
            }
        }
    }

    private fun setupBouncyCastle() {
        val provider: Provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)
            ?: // Web3j will set up the provider lazily when it's first used.
            return
        if (provider.javaClass == BouncyCastleProvider::class.java) {
            // BC with same package name, shouldn't happen in real life.
            return
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.insertProviderAt(BouncyCastleProvider(), 1)
    }

}
