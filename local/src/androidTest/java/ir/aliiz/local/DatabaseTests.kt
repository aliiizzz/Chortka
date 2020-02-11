package ir.aliiz.local

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.test.core.app.ApplicationProvider
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
        doTest {
            trDao.addHashtag(ir.aliiz.local.model.Hashtag("test", 1, null))
            trDao.addTransaction(
                ir.aliiz.local.model.TransactionInfo(
                    "1",
                    1000,
                    System.currentTimeMillis()
                )
            )
            trDao.addTransaction(
                ir.aliiz.local.model.TransactionInfo(
                    "2",
                    3400,
                    System.currentTimeMillis()
                )
            )
            trDao.addTransactionHashtag(ir.aliiz.local.model.TransactionHashtag(0, "test", "1"))
            trDao.addTransactionHashtag(ir.aliiz.local.model.TransactionHashtag(0, "test", "2"))
            val list = trDao.getHashtagsWithAmount()
            Assert.assertEquals(list.size, 1)
            Assert.assertEquals(list[0].amount, 4400)
        }
    }

    @Test
    fun hashtag_transactions() {
        doTest {
            trDao.addHashtag(ir.aliiz.local.model.Hashtag("test", 1, null))
            trDao.addTransaction(
                ir.aliiz.local.model.TransactionInfo(
                    "1",
                    1000,
                    System.currentTimeMillis()
                )
            )
            trDao.addTransaction(
                ir.aliiz.local.model.TransactionInfo(
                    "2",
                    3400,
                    System.currentTimeMillis()
                )
            )
            trDao.addTransactionHashtag(ir.aliiz.local.model.TransactionHashtag(0, "test", "1"))
            trDao.addTransactionHashtag(ir.aliiz.local.model.TransactionHashtag(0, "test", "2"))
            val q = db.query(SimpleSQLiteQuery("select * from TransactionHashtag left join Hashtag on TransactionHashtag.hashtagTitle = Hashtag.title left join TransactionInfo on TransactionHashtag.transactionId = TransactionInfo.id"))
        }
    }

    fun doTest(test: () -> Unit) {
        before()
        test()
        after()
    }
}