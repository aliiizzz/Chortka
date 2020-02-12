package ir.aliiz.domain_impl.di

import dagger.Component
import dagger.Module
import dagger.Provides
import ir.aliiz.di.AppComponent
import ir.aliiz.domain.TransactionRepo
import ir.aliiz.domain.usecase.UseCaseAddHashtag
import ir.aliiz.domain.usecase.UseCaseHashtags
import ir.aliiz.domain_impl.UseCaseAddHashtagImpl
import ir.aliiz.domain_impl.UseCaseHashtagsImpl
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
}