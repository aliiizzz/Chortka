package ir.aliiz.chortka.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.aliiz.chortka.local.model.Hashtag
import ir.aliiz.chortka.local.model.TransactionInfo

@Database(entities = [TransactionInfo::class, Hashtag::class], version = 1)
abstract class ChortkaDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}