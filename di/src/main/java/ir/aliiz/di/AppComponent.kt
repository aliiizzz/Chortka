package ir.aliiz.di

import android.app.Activity
import android.content.Context
import dagger.Component

@Component
interface AppComponent {
    companion object {
        fun getAppComponent(activity: Activity? , context: Context? = null): AppComponent = ((activity?.application ?: context!!.applicationContext) as Provider).getComponent()
    }
}