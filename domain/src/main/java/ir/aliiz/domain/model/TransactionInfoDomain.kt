package ir.aliiz.domain.model

data class TransactionInfoDomain(
    val id: String,
    val hashtags: List<String>,
    val amount: Long
)