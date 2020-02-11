package ir.aliiz.domain.model

data class HashtagDomain(
    val title: String,
    val type: Int,
    val formula: String?,
    val amount: Long = 0
)