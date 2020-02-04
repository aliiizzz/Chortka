package ir.aliiz.chortka.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.text.isDigitsOnly
import ir.aliiz.chortka.domain.model.TransactionInfoDomain
import ir.aliiz.chortka.repository.TransactionRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReplyBroadcastReceiver : BroadcastReceiver() {
    @Inject lateinit var transactionRepo: TransactionRepo


    override fun onReceive(p0: Context?, p1: Intent?) {
        (p0!!.applicationContext as App).apply {
            component.inject(this@ReplyBroadcastReceiver)
        }
        Toast.makeText(p0!!, "test", Toast.LENGTH_LONG).show()
        RemoteInput.getResultsFromIntent(p1).getString("reply_transaction")?.also {
            val words = it.split(" ")
            val hashtags = mutableListOf<String>()
            var amount = 0L
            words.forEach {
                if (it.contains("#")) hashtags.add(it.replace("#", ""))
                if (it.isDigitsOnly()) amount = it.toLong()
            }

            CoroutineScope(Dispatchers.IO).launch {
                transactionRepo.addTransaction(TransactionInfoDomain(hashtags, amount))
            }
            NotificationManagerCompat.from(p0).apply {
                val id = 101
                val notif = NotificationFactory.getNotification(p0)
                notify(id, notif)
            }
        }
    }
}