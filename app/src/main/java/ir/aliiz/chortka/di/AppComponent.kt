package ir.aliiz.chortka.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Component
import dagger.Module
import dagger.Provides
import ir.aliiz.chortka.FragmentHashtag
import ir.aliiz.chortka.FragmentTransactions
import ir.aliiz.chortka.MainActivity
import ir.aliiz.chortka.ReplyBroadcastReceiver
import ir.aliiz.chortka.local.ChortkaDatabase
import ir.aliiz.chortka.local.TransactionDao
import ir.aliiz.chortka.repository.TransactionRepo
import ir.aliiz.chortka.repository.TransactionRepoImpl

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(context: ReplyBroadcastReceiver)
    fun inject(context: FragmentHashtag)
    fun inject(context: FragmentTransactions)
}

@Module
class AppModule(val context: Context) {
    @Provides
    fun provideAppDatabase(): ChortkaDatabase = Room.databaseBuilder(context, ChortkaDatabase::class.java, "chortka").build()

    @Provides
    fun provideTransactionDao(db: ChortkaDatabase) = db.transactionDao()

    @Provides
    fun provideTransactionRepo(transactionDao: TransactionDao): TransactionRepo = TransactionRepoImpl(transactionDao)
}