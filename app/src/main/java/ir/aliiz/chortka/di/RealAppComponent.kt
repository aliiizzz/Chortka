package ir.aliiz.chortka.di

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ir.aliiz.chortka.presentation.App
import ir.aliiz.chortka.presentation.ReplyBroadcastReceiver
import ir.aliiz.chortka.presentation.main.MainFragment
import ir.aliiz.chortka.presentation.main.ViewModelMain
import ir.aliiz.common.ViewModelBuilder
import ir.aliiz.common.ViewModelKey
import ir.aliiz.di.AppComponent
import ir.aliiz.di.AppComponent.Companion.getAppComponent
import ir.aliiz.di.Provider
import ir.aliiz.local.di.LocalModule
import ir.aliiz.repository.di.RepoModule

@Component(dependencies = [AppComponent::class], modules = [MainModule::class, RepoModule::class, LocalModule::class, ViewModelBuilder::class])
interface AppModuleComponent {
    fun inject(context: MainFragment)
    fun inject(context: ReplyBroadcastReceiver)

    companion object {
        fun create(activity: Activity?, context: Context? = null): AppModuleComponent = DaggerAppModuleComponent.builder().appComponent(
            getAppComponent(activity, context)).localModule(LocalModule(activity ?: context!!)).build()
    }
}

@Module
class MainModule {
    @Provides
    @IntoMap
    @ViewModelKey(ViewModelMain::class)
    fun provideViewMoel(vm: ViewModelMain): ViewModel = vm
}
