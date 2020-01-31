package ir.aliiz.chortka.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class TransactionInfo(
    @PrimaryKey val id: String,
    val amount: Long
)

@Entity
data class Hashtag(@PrimaryKey(autoGenerate = true) val id: Long, val transactionId: String, val title: String, val type: Int)

data class TransactionWithHashtag(
    @Embedded val transactionInfo: TransactionInfo,
    @Relation(parentColumn = "id", entityColumn = "transactionId")
    val hashtags: List<Hashtag>
)