package ir.aliiz.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val migration_1_to_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("alter table TransactionInfo add createdAt integer not null default 0")
        database.execSQL("alter table Hashtag add formula text")
    }
}