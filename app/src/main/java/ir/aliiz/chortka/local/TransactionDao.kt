package ir.aliiz.chortka.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ir.aliiz.chortka.local.model.Hashtag
import ir.aliiz.chortka.local.model.TransactionInfo
import ir.aliiz.chortka.local.model.TransactionWithHashtag

@Dao
interface TransactionDao {
    @Insert
    fun addTransaction(param: TransactionInfo)

    @Insert
    fun addHashtag(param: Hashtag)

    @Query("select * from TransactionInfo")
    fun getTransactions(): List<TransactionWithHashtag>

    @Query("select * from Hashtag")
    fun getHashtags(): List<Hashtag>

    @Update
    fun updateHashtag(param: Hashtag)

    @Insert
    fun insertHashtag(param: Hashtag)
}