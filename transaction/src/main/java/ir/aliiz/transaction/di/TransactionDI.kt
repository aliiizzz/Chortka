package ir.aliiz.transaction.di

import androidx.lifecycle.ViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ir.aliiz.common.ViewModelKey
import ir.aliiz.di.AppComponent
import ir.aliiz.transaction.TransactionViewModel
import ir.aliiz.transaction.detail.HashtagResultDetailViewModel

@Component(dependencies = [AppComponent::class], modules = [TransactionModule::class])
interface TransactionComponent

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