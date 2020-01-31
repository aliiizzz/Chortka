package ir.aliiz.chortka

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationBroadcastReciever: BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        val t = p0
        Toast.makeText(p0!!, "test", Toast.LENGTH_LONG).show()
    }
}