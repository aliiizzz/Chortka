package ir.aliiz.domain.model

data class HashtagDomain(
    val title: String,
    val type: Int,
    val childs: List<HashtagDomain>,
    val amount: Long = 0
)