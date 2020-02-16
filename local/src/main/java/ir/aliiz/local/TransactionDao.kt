package ir.aliiz.local

import androidx.room.*
import ir.aliiz.local.model.*

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

    @Query("select title, type, sum(amount) as amount from TransactionHashtag left join Hashtag on TransactionHashtag.hashtagTitle = Hashtag.title left join TransactionInfo on TransactionHashtag.transactionId = TransactionInfo.id  where title = :id group by title")
    fun getHashtagsWithAmount(id: String): HashtagWithAmount

    @Update
    fun updateHashtag(param: Hashtag)

    @Insert
    fun insertHashtag(param: Hashtag)

    @Query("select title from TransactionHashtag inner join Hashtag on TransactionHashtag.hashtagTitle = Hashtag.title where TransactionHashtag.transactionId = :id")
    fun getTransactionHashtags(id: String): List<String>

    @Query("select TransactionInfo.id as id, TransactionInfo.amount as amount, TransactionInfo.createdAt as createdAt, Hashtag.title as hashtag from TransactionHashtag left join Hashtag on TransactionHashtag.hashtagTitle = Hashtag.title left join TransactionInfo on TransactionHashtag.transactionId = TransactionInfo.id where hashtag=:hashtag")
    fun getHashtagTransactions(hashtag: String): List<HashtagTransaction>

    @Query("select * from TransactionInfo where id = :param")
    fun getTransaction(param: String): TransactionInfo

    @Query("delete from TransactionHashtag where hashtagTitle = :hashtag and transactionId = :transactionId")
    fun removeHashtag(transactionId: String, hashtag: String)

    @Query("delete from TransactionHashtag where hashtagTitle = :title")
    fun temp(title: String = "somethibg")

    @Query("select parent from HashtagHashtag where child = :child")
    fun getHashtagRelations(child: String): List<String>

    @Insert
    fun addHashtagHashtag(hash: HashtagHashtag)
}