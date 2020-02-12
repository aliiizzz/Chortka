package ir.aliiz.common

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class CommonModule {

    @Provides
    fun provideAppDispatcher(): AppDispatchers = AppDispatchers(Dispatchers.Main, Dispatchers.IO)
}