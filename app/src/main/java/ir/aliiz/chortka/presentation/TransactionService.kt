package ir.aliiz.chortka.presentation

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder

class TransactionService : Service() {

    override fun onCreate() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("chortka", "chortka", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        val notif = NotificationFactory.getNotification(this)
        startForeground(101, notif)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? = null
}