package ir.aliiz.chortka.local

import androidx.room.*
import ir.aliiz.chortka.local.model.Hashtag
import ir.aliiz.chortka.local.model.HashtagWithAmount
import ir.aliiz.chortka.local.model.TransactionHashtag
import ir.aliiz.chortka.local.model.TransactionInfo

@Dao
interface TransactionDao {
    @Insert
    fun addTransaction(param: TransactionInfo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addHashtag(param: Hashtag)

    @Insert
    fun addTransactionHashtag(param: TransactionHashtag)

    @Query("select * from TransactionInfo")
    fun getTransactions(): List<TransactionInfo>

    @Query("select * from Hashtag")
    fun getHashtags(): List<Hashtag>

    @Query("select title, type, formula, sum(amount) as amount from TransactionHashtag left join Hashtag on TransactionHashtag.hashtagTitle = Hashtag.title left join TransactionInfo on TransactionHashtag.transactionId = TransactionInfo.id group by title")
    fun getHashtagsWithAmount(): List<HashtagWithAmount>
    @Update
    fun updateHashtag(param: Hashtag)

    @Insert
    fun insertHashtag(param: Hashtag)

    @Query("select title from TransactionHashtag inner join Hashtag on TransactionHashtag.hashtagTitle = Hashtag.title where TransactionHashtag.transactionId = :id")
    fun getTransactionHashtags(id: String): List<String>
}