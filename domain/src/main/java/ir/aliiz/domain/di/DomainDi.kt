package ir.aliiz.domain.di

import dagger.Component
import dagger.Module
import dagger.Provides
import ir.aliiz.di.AppComponent
import ir.aliiz.domain.TransactionRepo
import ir.aliiz.domain.usecase.UseCaseHashtags

@Component(dependencies = [AppComponent::class], modules = [UseCaseModule::class])
interface DomainComponent

@Module
class UseCaseModule {
    @Provides
    fun provideHashtag(transactionRepo: TransactionRepo): UseCaseHashtags = UseCaseHashtags(transactionRepo)
}