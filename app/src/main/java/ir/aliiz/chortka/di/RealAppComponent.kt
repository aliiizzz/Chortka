package ir.aliiz.chortka.di

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ir.aliiz.chortka.presentation.App
import ir.aliiz.chortka.presentation.main.MainFragment
import ir.aliiz.chortka.presentation.main.ViewModelMain
import ir.aliiz.common.ViewModelKey
import ir.aliiz.di.AppComponent
import ir.aliiz.di.AppComponent.Companion.getAppComponent
import ir.aliiz.di.Provider

@Component(dependencies = [AppComponent::class], modules = [MainModule::class])
interface AppModuleComponent {
    fun inject(context: MainFragment)

    companion object {
        fun create(activity: Activity?, context: Context? = null): AppModuleComponent = DaggerAppModuleComponent.builder().appComponent(
            getAppComponent(activity, context)).build()
    }
}

@Module
class MainModule {
    @Provides
    @IntoMap
    @ViewModelKey(ViewModelMain::class)
    fun provideViewMoel(vm: ViewModelMain): ViewModel = vm
}
