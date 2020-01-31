package ir.aliiz.chortka.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionInfo(
    @PrimaryKey val id: String,
    val amount: Long
)

@Entity
data class Hashtag(@PrimaryKey val title: String, val type: Int)


@Entity
data class TransactionHashtag(@PrimaryKey(autoGenerate = true) val id: Long, val hashtagTitle: String, val transactionId: String)