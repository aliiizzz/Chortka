package ir.aliiz.chortka

import android.app.Application
import ir.aliiz.chortka.di.AppComponent
import ir.aliiz.chortka.di.AppModule
import ir.aliiz.chortka.di.DaggerAppComponent

class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}