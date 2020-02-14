package ir.aliiz.transaction.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ir.aliiz.common.CommonModule
import ir.aliiz.common.ViewModelBuilder
import ir.aliiz.common.ViewModelKey
import ir.aliiz.di.AppComponent
import ir.aliiz.domain_impl.di.UseCaseModule
import ir.aliiz.local.di.LocalModule
import ir.aliiz.repository.di.RepoModule
import ir.aliiz.transaction.TransactionViewModel
import ir.aliiz.transaction.TransactionsFragment
import ir.aliiz.transaction.detail.TransactionDetailFragment
import ir.aliiz.transaction.detail.TransactionDetailViewModel
import ir.aliiz.transaction.result.HashtagResultDetailFragment
import ir.aliiz.transaction.result.HashtagResultDetailViewModel

@Component(dependencies = [AppComponent::class], modules = [TransactionModule::class,
    RepoModule::class, LocalModule::class, ViewModelBuilder::class, CommonModule::class, UseCaseModule::class])
interface TransactionComponent {
    fun inject(context: TransactionsFragment)
    fun inject(context: HashtagResultDetailFragment)
    fun inject(context: TransactionDetailFragment)

    companion object {
        fun create(activity: Activity): TransactionComponent = DaggerTransactionComponent.builder().appComponent(
            AppComponent.getAppComponent(activity)).localModule(LocalModule(activity)).build()
    }
}

@Module
class TransactionModule {
    @Provides
    @IntoMap
    @ViewModelKey(TransactionViewModel::class)
    fun provideViewModel(vm: TransactionViewModel): ViewModel = vm

    @Provides
    @IntoMap
    @ViewModelKey(HashtagResultDetailViewModel::class)
    fun provideHashtagResultDetailViewmodel(vm: HashtagResultDetailViewModel): ViewModel = vm

    @Provides
    @IntoMap
    @ViewModelKey(TransactionDetailViewModel::class)
    fun provideTransactionDetailViewmodel(vm: TransactionDetailViewModel): ViewModel = vm
}