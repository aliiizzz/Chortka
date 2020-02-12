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
import ir.aliiz.local.di.LocalModule
import ir.aliiz.repository.di.RepoModule
import ir.aliiz.transaction.TransactionViewModel
import ir.aliiz.transaction.TransactionsFragment
import ir.aliiz.transaction.detail.HashtagResultDetailViewModel

@Component(dependencies = [AppComponent::class], modules = [TransactionModule::class,
    RepoModule::class, LocalModule::class, ViewModelBuilder::class, CommonModule::class])
interface TransactionComponent {
    fun inject(context: TransactionsFragment)

    companion object {
        fun create(activity: Activity): TransactionComponent = DaggerTransactionComponent.builder().appComponent(
            AppComponent.getAppComponent(activity)).build()
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
}