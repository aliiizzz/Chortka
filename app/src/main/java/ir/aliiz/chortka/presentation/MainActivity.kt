package ir.aliiz.chortka.presentation

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.aliiz.chortka.R
import ir.aliiz.chortka.presentation.hashtag.FragmentHashtag
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, TransactionService::class.java))
        } else {
            startService(Intent(this, TransactionService::class.java))
        }

        bottom_navigation_main.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_hashtag -> supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_main,
                    FragmentHashtag(), "hashtag").commit().let { true }
                R.id.action_transaction -> supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_main,
                    FragmentTransactions(), "transactions").commit().let { true }
                else -> supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_main,
                    FragmentHashtag(), "hashtag").commit().let { true }
            }
        }


    }

}
