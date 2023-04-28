package com.example.androidsemester4.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidsemester4.App
import javax.inject.Inject

class SearchWeatherViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    @Inject
    lateinit var searchWeatherViewModel: SearchWeatherViewModel

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        (application.applicationContext as App).appComponent.inject(this)
        return searchWeatherViewModel as T
    }
}