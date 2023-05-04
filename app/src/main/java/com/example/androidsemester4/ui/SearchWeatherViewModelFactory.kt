package com.example.androidsemester4.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidsemester4.domain.GetNearCitiesUseCase

class SearchWeatherViewModelFactory(val application: Application, private val getNearCitiesUseCase: GetNearCitiesUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val searchWeatherViewModel=SearchWeatherViewModel(application, getNearCitiesUseCase)
        return searchWeatherViewModel as T
    }
}