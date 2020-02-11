package ir.aliiz.chortka.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ir.aliiz.chortka.domain.usecase.UseCaseHashtags
import ir.aliiz.chortka.presentation.hashtag.FragmentHashtag
import ir.aliiz.chortka.local.ChortkaDatabase
import ir.aliiz.chortka.local.TransactionDao
import ir.aliiz.chortka.presentation.*
import ir.aliiz.chortka.presentation.hashtag.ViewModelHashtag
import ir.aliiz.chortka.presentation.hashtag.add.AddHashtagFragment
import ir.aliiz.chortka.presentation.hashtag.add.AddHashtagViewModel
import ir.aliiz.chortka.presentation.main.FragmentMain
import ir.aliiz.chortka.presentation.main.ViewModelMain
import ir.aliiz.chortka.presentation.transaction.TransactionViewModel
import ir.aliiz.chortka.presentation.transaction.TransactionsFragment
import ir.aliiz.chortka.repository.TransactionRepo
import ir.aliiz.chortka.repository.TransactionRepoImpl
import kotlinx.coroutines.Dispatchers

@Component(modules = [ViewModelBuilder::class, AppModule::class,
    HashtagModule::class, MainModule::class, AddHashtagModule::class,
    UseCaseModule::class, TransactionModule::class])
interface AppComponent {
    fun inject(context: ReplyBroadcastReceiver)
    fun inject(context: FragmentHashtag)
    fun inject(context: TransactionsFragment)
    fun inject(context: FragmentMain)
    fun inject(context: AddHashtagFragment)
}

@Module
class AppModule(val context: Context) {
    @Provides
    fun provideAppDatabase(): ChortkaDatabase = Room.databaseBuilder(context, ChortkaDatabase::class.java, "chortka").build()

    @Provides
    fun provideTransactionDao(db: ChortkaDatabase) = db.transactionDao()

    @Provides
    fun provideTransactionRepo(transactionDao: TransactionDao): TransactionRepo = TransactionRepoImpl(transactionDao)

    @Provides
    fun provideDispatcher(): AppDispatchers = AppDispatchers(Dispatchers.Main, Dispatchers.IO)
}

@Module
class HashtagModule {

    @Provides
    @IntoMap
    @ViewModelKey(ViewModelHashtag::class)
    fun provideHashtagViewmodel(vm: ViewModelHashtag): ViewModel = vm
}

@Module
class MainModule {
    @Provides
    @IntoMap
    @ViewModelKey(ViewModelMain::class)
    fun provideViewMoel(vm: ViewModelMain): ViewModel = vm
}

@Module
class AddHashtagModule {
    @Provides
    @IntoMap
    @ViewModelKey(AddHashtagViewModel::class)
    fun provideViewMoel(vm: AddHashtagViewModel): ViewModel = vm
}

@Module
class UseCaseModule {
    @Provides
    fun provideHashtag(transactionRepo: TransactionRepo): UseCaseHashtags = UseCaseHashtags(transactionRepo)
}

@Module
class TransactionModule {
    @Provides
    @IntoMap
    @ViewModelKey(TransactionViewModel::class)
    fun provideViewModel(vm: TransactionViewModel): ViewModel = vm
}