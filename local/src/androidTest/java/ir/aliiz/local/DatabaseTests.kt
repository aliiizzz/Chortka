package ir.aliiz.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.test.core.app.ApplicationProvider
import ir.aliiz.local.model.Hashtag
import ir.aliiz.local.model.HashtagHashtag
import ir.aliiz.local.model.TransactionHashtag
import ir.aliiz.local.model.TransactionInfo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DatabaseTests {

    private lateinit var trDao: TransactionDao
    private lateinit var db: ChortkaDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ChortkaDatabase::class.java).build()
        trDao = db.transactionDao()
    }

    fun before() {
        delete("Hashtag")
        delete("TransactionInfo")
        delete("TransactionHashtag")
    }

    fun after() {
        delete("Hashtag")
        delete("TransactionInfo")
        delete("TransactionHashtag")
    }

    fun delete(tableName: String) {
        db.openHelper.writableDatabase.delete(tableName, "", arrayOf())
    }
    @After
    fun cleanup() {
        db.close()
    }

    @Test
    fun hashtag_with_amount() {
        doTest({
            before()
        }, {
            after()
        }) {
            trDao.addHashtag(Hashtag("test", 1))
            trDao.addTransaction(
                TransactionInfo(
                    "1",
                    1000,
                    System.currentTimeMillis()
                )
            )
            trDao.addTransaction(
                TransactionInfo(
                    "2",
                    3400,
                    System.currentTimeMillis()
                )
            )
            trDao.addTransactionHashtag(ir.aliiz.local.model.TransactionHashtag(0, "test", "1"))
            trDao.addTransactionHashtag(ir.aliiz.local.model.TransactionHashtag(0, "test", "2"))
//            val list = trDao.getHashtagsWithAmount()
//            Assert.assertEquals(list.size, 1)
//            Assert.assertEquals(list[0].amount, 4400)
        }
    }

    @Test
    fun hashtag_transactions() {
        doTest({
           before()
        }, {
            after()
        }) {
            trDao.addHashtag(Hashtag("test", 1))
            trDao.addTransaction(
                TransactionInfo(
                    "1",
                    1000,
                    System.currentTimeMillis()
                )
            )
            trDao.addTransaction(
                TransactionInfo(
                    "2",
                    3400,
                    System.currentTimeMillis()
                )
            )
            trDao.addTransactionHashtag(TransactionHashtag(0, "test", "1"))
            trDao.addTransactionHashtag(TransactionHashtag(0, "test", "2"))
            val q = db.query(SimpleSQLiteQuery("select * from TransactionHashtag left join Hashtag on TransactionHashtag.hashtagTitle = Hashtag.title left join TransactionInfo on TransactionHashtag.transactionId = TransactionInfo.id"))
        }
    }

    @Test
    fun hashtag_relations() {
        doTest({
            insertHashtag(Hashtag("test", 0))
            insertHashtag(Hashtag("test2", 0))
            insertHashtag(Hashtag("test3", 0))
            insertHashtag(Hashtag("tab", 0))
            insertTransaction(TransactionInfo("1", 1000, System.currentTimeMillis()))
            insertTransaction(TransactionInfo("2", 2000, System.currentTimeMillis()))
            insertTransaction(TransactionInfo("3", 3000, System.currentTimeMillis()))
            insertTransactionHashtag(TransactionHashtag(0, "test", "1"))
            insertTransactionHashtag(TransactionHashtag(0, "test", "2"))
            insertHashtagHashtag(HashtagHashtag(0, "tab", "test"))
            insertHashtagHashtag(HashtagHashtag(0, "tab", "test2"))
        }, {
            delete("HashtagHashtag")
            delete("TransactionHashtag")
            delete("TransactionInfo")
            delete("Hashtag")
        }) {
            val result = trDao.getHashtagRelations("tab")
            val sum = trDao.getHashtagsWithAmount("tab")
            Assert.assertEquals(result, listOf("test", "test2"))
            Assert.assertNull(sum)
        }
    }

    private fun insertHashtagHashtag(hashtagHashtag: HashtagHashtag) {
        trDao.addHashtagHashtag(hashtagHashtag)
    }

    private fun insertTransactionHashtag(transactionHashtag: TransactionHashtag) {
        trDao.addTransactionHashtag(transactionHashtag)
    }

    private fun insertTransaction(transactionInfo: TransactionInfo) {
        trDao.addTransaction(transactionInfo)
    }

    private fun insertHashtag(hashtag: Hashtag) {
        trDao.addHashtag(hashtag)
    }

    fun doTest(before: () -> Unit, after: () -> Unit, test: () -> Unit) {
        before()
        test()
        after()
    }
}