package ir.aliiz.local

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val migration_1_to_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("alter table TransactionInfo add createdAt integer not null default 0")
        database.execSQL("alter table Hashtag add formula text")
    }
}

val migration_2_to_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.apply {

            val cur = this.query("select * from Hashtag")
            execSQL("create table HashtagHashtag(id integer not null primary key autoincrement, child text not null references Hashtag(title), parent text not null references Hashtag(title))")
            execSQL("create table HashtagTemp(title text not null primary key, type integer not null)")
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