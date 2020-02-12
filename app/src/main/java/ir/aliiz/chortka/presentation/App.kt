package ir.aliiz.chortka.presentation

import android.app.Application
import ir.aliiz.di.AppComponent
import ir.aliiz.di.DaggerAppComponent
import ir.aliiz.di.Provider

class App : Application(), Provider {

    private lateinit var diComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        diComponent = DaggerAppComponent.builder().build()
    }

    override fun getComponent(): AppComponent = diComponent
}