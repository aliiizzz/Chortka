package ir.aliiz.chortka.presentation

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.aliiz.chortka.R
import ir.aliiz.hashtag.FragmentHashtag

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, TransactionService::class.java))
        } else {
            startService(Intent(this, TransactionService::class.java))
        }

    }

}
