package com.example.androidsemester4

import android.app.Application
import com.example.androidsemester4.BuildConfig
import timber.log.Timber

class App:Application() {
    override fun onCreate(){
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}
