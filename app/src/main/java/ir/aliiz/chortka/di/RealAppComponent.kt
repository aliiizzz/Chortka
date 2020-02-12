package ir.aliiz.chortka.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ir.aliiz.domain.usecase.UseCaseHashtags
import ir.aliiz.hashtag.FragmentHashtag
import ir.aliiz.local.ChortkaDatabase
import ir.aliiz.local.TransactionDao
import ir.aliiz.chortka.presentation.*
import ir.aliiz.hashtag.ViewModelHashtag
import ir.aliiz.hashtag.add.AddHashtagFragment
import ir.aliiz.hashtag.add.AddHashtagViewModel
import ir.aliiz.hashtag.result.HashtagResultFragment
import ir.aliiz.hashtag.result.HashtagResultViewModel
import ir.aliiz.hashtag.result.detail.HashtagResultDetailFragment
import ir.aliiz.hashtag.result.detail.HashtagResultDetailViewModel
import ir.aliiz.chortka.presentation.main.MainFragment
import ir.aliiz.chortka.presentation.main.ViewModelMain
import ir.aliiz.transaction.TransactionViewModel
import ir.aliiz.transaction.TransactionsFragment
import ir.aliiz.domain.TransactionRepo
import ir.aliiz.repository.TransactionRepoImpl
import kotlinx.coroutines.Dispatchers

@Component(modules = [ir.aliiz.common.ViewModelBuilder::class, AppModule::class,
    HashtagModule::class, MainModule::class, AddHashtagModule::class,
    UseCaseModule::class, TransactionModule::class])
interface RealAppComponent {
    fun inject(context: ReplyBroadcastReceiver)
    fun inject(context: ir.aliiz.hashtag.FragmentHashtag)
    fun inject(context: ir.aliiz.transaction.TransactionsFragment)
    fun inject(context: MainFragment)
    fun inject(context: ir.aliiz.hashtag.add.AddHashtagFragment)
    fun inject(context: ir.aliiz.hashtag.result.HashtagResultFragment)
    fun inject(context: ir.aliiz.hashtag.result.detail.HashtagResultDetailFragment)
}

@Module
class AppModule(val context: Context) {
    @Provides
    fun provideAppDatabase(): ChortkaDatabase = Room.databaseBuilder(context, ChortkaDatabase::class.java, "chortka").build()

    @Provides
    fun provideTransactionDao(db: ChortkaDatabase) = db.transactionDao()

    @Provides
    fun provideTransactionRepo(transactionDao: TransactionDao): TransactionRepo =
        TransactionRepoImpl(transactionDao)

    @Provides
    fun provideDispatcher(): ir.aliiz.common.AppDispatchers =
        ir.aliiz.common.AppDispatchers(Dispatchers.Main, Dispatchers.IO)
}

@Module
class HashtagModule {

    @Provides
    @IntoMap
    @ir.aliiz.common.ViewModelKey(ir.aliiz.hashtag.ViewModelHashtag::class)
    fun provideHashtagViewmodel(vm: ir.aliiz.hashtag.ViewModelHashtag): ViewModel = vm

    @Provides
    @IntoMap
    @ir.aliiz.common.ViewModelKey(ir.aliiz.hashtag.result.HashtagResultViewModel::class)
    fun provideHashtagResultViewmodel(vm: ir.aliiz.hashtag.result.HashtagResultViewModel): ViewModel = vm

    @Provides
    @IntoMap
    @ir.aliiz.common.ViewModelKey(ir.aliiz.hashtag.result.detail.HashtagResultDetailViewModel::class)
    fun provideHashtagResultDetailViewmodel(vm: ir.aliiz.hashtag.result.detail.HashtagResultDetailViewModel): ViewModel = vm
}

@Module
class MainModule {
    @Provides
    @IntoMap
    @ir.aliiz.common.ViewModelKey(ViewModelMain::class)
    fun provideViewMoel(vm: ViewModelMain): ViewModel = vm
}

@Module
class AddHashtagModule {
    @Provides
    @IntoMap
    @ir.aliiz.common.ViewModelKey(ir.aliiz.hashtag.add.AddHashtagViewModel::class)
    fun provideViewMoel(vm: ir.aliiz.hashtag.add.AddHashtagViewModel): ViewModel = vm
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
    @ir.aliiz.common.ViewModelKey(ir.aliiz.transaction.TransactionViewModel::class)
    fun provideViewModel(vm: ir.aliiz.transaction.TransactionViewModel): ViewModel = vm
}