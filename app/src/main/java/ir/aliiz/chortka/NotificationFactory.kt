package ir.aliiz.chortka

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

object NotificationFactory {
    fun getNotification(context: Context): Notification {
        val addTransaction = Intent(context, NotificationBroadcastReciever::class.java).apply {
            action = "add_transation"
            putExtra(Notification.EXTRA_NOTIFICATION_ID, 0)
        }
        val pending = PendingIntent.getBroadcast(context, 123, addTransaction, 0)
        val reply = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH)
            RemoteInput.Builder("reply_transaction").setLabel("Amount then your #hashtags").build() else null
        val replyPending = reply?.let {
            PendingIntent.getBroadcast(context, 124,
                Intent(context, ReplyBroadcastReceiver::class.java).apply {
                    putExtra(Notification.EXTRA_NOTIFICATION_ID, 100)
                }, PendingIntent.FLAG_UPDATE_CURRENT)
        }?.let {
            NotificationCompat.Action.Builder(R.drawable.test, "Add with Hashtag", it).addRemoteInput(reply).build()
        }
        val notif = NotificationCompat.Builder(context, "chortka")
            .apply {
                setSmallIcon(R.drawable.test)
                setContentTitle("Transaction")
                setContentText("You can add transaction with Add with Hashtag")
                setPriority(NotificationCompat.PRIORITY_DEFAULT)
                addAction(R.drawable.test, "Add Page", pending)
                replyPending?.also {
                    addAction(it)
                }
            }.build()

        return notif
    }

}