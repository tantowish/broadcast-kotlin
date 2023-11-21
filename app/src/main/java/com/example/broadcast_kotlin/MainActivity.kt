package com.example.broadcast_kotlin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.broadcast_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val channelId = "TEST_NOTIF"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            btnNotification.setOnClickListener{
                val flag = if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    PendingIntent.FLAG_IMMUTABLE
                }
                else{0}

                val intent = Intent(this@MainActivity, NotifReceiver::class.java).putExtra("MESSAGE",
                    "Baca selengkapnya..")
        
                val pendingIntent = PendingIntent.getBroadcast(this@MainActivity,0,intent,flag)

                val builder = NotificationCompat.Builder(this@MainActivity,channelId)
                    .setSmallIcon(R.drawable.ic_notif)
                    .setContentTitle("Notification Test")
//                    .setContentText("Hello World!")
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .addAction(0,"Baca Notif", pendingIntent)
                    .setStyle(NotificationCompat.BigTextStyle()
                        .bigText("Hello World Big text"))

                val notifManager = getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                    val notifChannel = NotificationChannel(
                        channelId,
                        "Notification Test",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )

                    with(notifManager){
                        createNotificationChannel(notifChannel)
                        notify(0, builder.build())
                    }
                }
                else{
                    notifManager.notify(0, builder.build())
                }
            }
        }
    }
}