package com.example.androidsemester4.di

import android.app.Application
import com.example.androidsemester4.ui.SearchWeatherViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(val application: Application) {
    @Provides
    fun provideFactory(
        application: Application
    ):SearchWeatherViewModelFactory=SearchWeatherViewModelFactory(application)
    @Provides
    fun provideApplication():Application=application
}