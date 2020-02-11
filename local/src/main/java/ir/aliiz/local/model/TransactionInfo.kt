package ir.aliiz.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionInfo(
    @PrimaryKey val id: String,
    val amount: Long,
    val createdAt: Long
)

@Entity
data class Hashtag(@PrimaryKey val title: String, val type: Int, val formula: String?)

@Entity
data class TransactionHashtag(@PrimaryKey(autoGenerate = true) val id: Long, val hashtagTitle: String, val transactionId: String)


data class HashtagWithAmount(val title: String, val type: Int, val formula: String?, val amount: Long)

data class HashtagTransaction(val id: String, val amount: Long, val createdAt: Long, val hashtag: String)