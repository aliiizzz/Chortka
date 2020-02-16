package ir.aliiz.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class TransactionInfo(
    @PrimaryKey val id: String,
    val amount: Long,
    val createdAt: Long
)

@Entity
data class Hashtag(@PrimaryKey val title: String, val type: Int)

@Entity(foreignKeys = [
    ForeignKey(entity = Hashtag::class, parentColumns = ["title"], childColumns = ["child"]),
    ForeignKey(entity = Hashtag::class, parentColumns = ["title"], childColumns = ["parent"])
])
data class HashtagHashtag(@PrimaryKey(autoGenerate = true) val id: Int,
                          val child: String,
                          val parent: String)

@Entity
data class TransactionHashtag(@PrimaryKey(autoGenerate = true) val id: Long, val hashtagTitle: String, val transactionId: String)


data class HashtagWithAmount(val title: String, val type: Int, val amount: Long)

data class HashtagTransaction(val id: String, val amount: Long, val createdAt: Long, val hashtag: String)