package ir.aliiz.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.test.core.app.ApplicationProvider
import ir.aliiz.local.model.Hashtag
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
            trDao.addHashtag(Hashtag("test", 1))
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
            trDao.addHashtag(Hashtag("test", 1))
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

    @Test
    fun test() {
        doTest {
            db.openHelper.writableDatabase.apply {
                insert("Hashtag", SQLiteDatabase.CONFLICT_FAIL, ContentValues().apply {
                    this.put("title", "test")
                    put("type", 0)
                })
                insert("Hashtag", SQLiteDatabase.CONFLICT_FAIL, ContentValues().apply {
                    this.put("title", "test2")
                    put("type", 0)
                })

                insert("Hashtag", SQLiteDatabase.CONFLICT_FAIL, ContentValues().apply {
                    this.put("title", "test3")
                    put("type", 0)
                    put("formula", "test2")
                })

                val cur = this.query("select * from Hashtag")
                execSQL("create table HashtagHashtag(id integer primary key autoincrement, child text references Hashtag(title), parent text references Hashtag(title))")
                execSQL("create table HashtagTemp(title text primary key, type integer not null)")
                if (cur.moveToFirst()) {
                    do {
                        val title = cur.getString(cur.getColumnIndex("title"))
                        val type = cur.getInt(cur.getColumnIndex("type"))
                        val formule = cur.getString(cur.getColumnIndex("formula"))
                        this.insert("HashtagTemp", SQLiteDatabase.CONFLICT_FAIL, ContentValues().apply {
                            put("title", title)
                            put("type", type)
                        })
                        if (formule != null) {
                            val split = formule.split(",")
                            split.forEach {
                                this.insert("HashtagHashtag", SQLiteDatabase.CONFLICT_FAIL, ContentValues().apply {
                                    put("child", title)
                                    put("parent", it)
                                })
                            }
                        }
                    } while (cur.moveToNext())
                }

                execSQL("drop table Hashtag")
                execSQL("alter table HashtagTemp rename to Hashtag")
            }
        }
    }

    fun doTest(test: () -> Unit) {
        before()
        test()
        after()
    }
}