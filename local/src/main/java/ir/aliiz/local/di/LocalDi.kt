package ir.aliiz.local.di

import android.content.Context
import androidx.room.Room
import dagger.Component
import dagger.Module
import dagger.Provides
import ir.aliiz.di.AppComponent
import ir.aliiz.local.migration_1_to_2

@Component(dependencies = [AppComponent::class], modules = [LocalModule::class])
interface LocalComponent

@Module
class LocalModule(val context: Context) {
    @Provides
    fun provideAppDatabase(): ir.aliiz.local.ChortkaDatabase = Room.databaseBuilder(context, ir.aliiz.local.ChortkaDatabase::class.java, "chortka")
        .addMigrations(migration_1_to_2).build()

    @Provides
    fun provideTransactionDao(db: ir.aliiz.local.ChortkaDatabase) = db.transactionDao()

}