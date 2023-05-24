package com.example.androidsemester4

import android.app.Application
import com.example.androidsemester4.di.ApplicationModule
import com.example.androidsemester4.di.DaggerApplicationComponent
import timber.log.Timber

class App : Application() {
    val appComponent =
        DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
