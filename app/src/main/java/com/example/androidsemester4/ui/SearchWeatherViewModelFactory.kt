package com.example.androidsemester4.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidsemester4.ui.mvi.mainPage.ViewModelMVI

class SearchWeatherViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModelMVI(
            application
        ) as T
    }
}
