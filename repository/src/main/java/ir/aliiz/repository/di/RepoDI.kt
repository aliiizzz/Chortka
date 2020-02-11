package ir.aliiz.repository.di

import dagger.Component
import dagger.Module
import dagger.Provides
import ir.aliiz.di.AppComponent
import ir.aliiz.domain.TransactionRepo
import ir.aliiz.local.TransactionDao
import ir.aliiz.repository.TransactionRepoImpl

@Component(dependencies = [AppComponent::class], modules = [RepoModule::class])
interface RepoComponent

@Module
class RepoModule {
    @Provides
    fun provideTransactionRepo(transactionDao: TransactionDao): TransactionRepo =
        TransactionRepoImpl(transactionDao)
}