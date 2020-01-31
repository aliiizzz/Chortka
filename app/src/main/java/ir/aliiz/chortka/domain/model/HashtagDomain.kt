package ir.aliiz.chortka.domain.model

data class HashtagDomain(
    val id: Long,
    val transactionId: String,
    val title: String,
    val type: Int
)