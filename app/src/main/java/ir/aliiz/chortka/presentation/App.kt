package ir.aliiz.chortka.presentation

import android.app.Application
import ir.aliiz.chortka.di.AppModule
import ir.aliiz.chortka.di.DaggerRealAppComponent
import ir.aliiz.chortka.di.RealAppComponent
import ir.aliiz.di.AppComponent
import ir.aliiz.di.DaggerAppComponent
import ir.aliiz.di.Provider

class App : Application(), Provider {

    private lateinit var diComponent: AppComponent
    lateinit var component: RealAppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerRealAppComponent.builder().appModule(AppModule(this)).build()
        diComponent = DaggerAppComponent.builder().build()
    }

    override fun getComponent(): AppComponent = diComponent
}