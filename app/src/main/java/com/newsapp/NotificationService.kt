package com.newsapp

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.newsapp.activities.main.MainActivity
import java.util.*


class NotificationService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int,  startId: Int): Int {
        super.onStart(intent, startId)
        val myTask = MyTimerTask(applicationContext)
        val myTimer = Timer()
        myTimer.schedule(myTask, 50000, 1500)
        Log.d("NotificationService: ", "Started==========")
        return Service.START_NOT_STICKY;
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    class MyTimerTask(val context: Context) : TimerTask() {
        override fun run() {
            Log.d("NotificationService: ", "Notify ==========")
            generateNotification(context, "Đã đến lúc cập nhật tin tức hằng ngày...")
        }

        private fun generateNotification(context: Context, message: String) {
            val icon: Int = R.drawable.ic_notifications_black_24dp
            val `when` = System.currentTimeMillis()
            val appname: String = context.getResources().getString(R.string.app_name)
            val notificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val currentapiVersion = Build.VERSION.SDK_INT
            val notification: Notification
            val contentIntent = PendingIntent.getActivity(
                context, 0,
                Intent(context, MainActivity::class.java), 0
            )

            if (currentapiVersion < Build.VERSION_CODES.HONEYCOMB) {
                notification = Notification(icon, message, 0)
                notification.flags = Notification.FLAG_AUTO_CANCEL
                notificationManager.notify(`when`.toInt(), notification)
            } else {
                val builder = NotificationCompat.Builder(
                    context
                )
                notification = builder.setContentIntent(contentIntent)
                    .setSmallIcon(icon).setTicker(appname).setWhen(0)
                    .setAutoCancel(true).setContentTitle(appname)
                    .setContentText(message).build()
                notificationManager.notify(`when`.toInt(), notification)
            }
        }
    }

}

