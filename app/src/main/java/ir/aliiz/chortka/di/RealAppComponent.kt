package ir.aliiz.chortka.di

import androidx.lifecycle.ViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ir.aliiz.chortka.presentation.main.ViewModelMain
import ir.aliiz.common.ViewModelKey
import ir.aliiz.di.AppComponent

@Component(dependencies = [AppComponent::class], modules = [MainModule::class])
interface AppModuleComponent

@Module
class MainModule {
    @Provides
    @IntoMap
    @ViewModelKey(ViewModelMain::class)
    fun provideViewMoel(vm: ViewModelMain): ViewModel = vm
}
