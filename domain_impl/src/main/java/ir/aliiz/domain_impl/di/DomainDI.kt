package ir.aliiz.domain_impl.di

import dagger.Component
import dagger.Module
import dagger.Provides
import ir.aliiz.di.AppComponent
import ir.aliiz.domain.TransactionRepo
import ir.aliiz.domain.usecase.*
import ir.aliiz.domain_impl.*
import ir.aliiz.local.di.LocalModule
import ir.aliiz.repository.di.RepoModule

@Component(dependencies = [AppComponent::class], modules = [UseCaseModule::class, RepoModule::class,
    LocalModule::class])
interface DomainImplComponent

@Module
class UseCaseModule {
    @Provides
    fun provideHashtag(transactionRepo: TransactionRepo): UseCaseHashtags =
        UseCaseHashtagsImpl(transactionRepo)

    @Provides
    fun provideAddHashtag(transactionRepo: TransactionRepo): UseCaseAddHashtag = UseCaseAddHashtagImpl(transactionRepo)

    @Provides
    fun provideTransactionDetailUseCase(transactionRepo: TransactionRepo): TransactionDetailUseCase = TransactionDetaiUseCaseImpl(transactionRepo)

    @Provides
    fun provideRemoveHashtag(transactionRepo: TransactionRepo): RemoveHashtagUseCase = RemoveHashtagUseCaseImpl(transactionRepo)

    @Provides
    fun provideAddTransactionHashtag(transactionRepo: TransactionRepo): AddHashtagUseCase = AddHashtagUseCaseImpl(transactionRepo)
}