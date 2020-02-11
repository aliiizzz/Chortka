package ir.aliiz.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.aliiz.local.model.Hashtag
import ir.aliiz.local.model.TransactionHashtag
import ir.aliiz.local.model.TransactionInfo

@Database(entities = [TransactionInfo::class, Hashtag::class, TransactionHashtag::class], version = 1)
abstract class ChortkaDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}