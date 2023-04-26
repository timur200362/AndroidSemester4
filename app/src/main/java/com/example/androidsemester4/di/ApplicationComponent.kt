package com.example.androidsemester4.di


import com.example.androidsemester4.ui.SearchWeatherFragment
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(fragment: SearchWeatherFragment)
}