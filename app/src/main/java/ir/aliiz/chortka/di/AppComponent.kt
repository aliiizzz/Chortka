package ir.aliiz.chortka.di

import android.content.Context
import androidx.room.Room
import dagger.Component
import dagger.Module
import dagger.Provides
import ir.aliiz.chortka.presentation.hashtag.FragmentHashtag
import ir.aliiz.chortka.presentation.FragmentTransactions
import ir.aliiz.chortka.presentation.ReplyBroadcastReceiver
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