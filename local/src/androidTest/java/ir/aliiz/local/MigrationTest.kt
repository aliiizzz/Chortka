package ir.aliiz.local

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MigrationTest {

    val DB_NAME = "test_db"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
        ChortkaDatabase::class.java.canonicalName, FrameworkSQLiteOpenHelperFactory())

    @Test
    fun migrate_2_to_3() {
        var db = helper.createDatabase(DB_NAME,2).apply {
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
            close()
        }

        db = helper.runMigrationsAndValidate(DB_NAME, 3, true, migration_2_to_3)
        val cur = db.query("select * from HashtagHashtag")
        var child = ""
        if (cur.moveToFirst()) {
            do {
                child = cur.getString(cur.getColumnIndex("child"))
            } while (cur.moveToNext())
        }

        Assert.assertEquals(child, "test3")
    }
}