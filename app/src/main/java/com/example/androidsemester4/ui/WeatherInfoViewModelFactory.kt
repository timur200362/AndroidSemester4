package com.example.androidsemester4.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidsemester4.domain.LoadWeatherUseCase

class WeatherInfoViewModelFactory(private val loadWeatherUseCase: LoadWeatherUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val weatherInfoViewModel = WeatherInfoViewModel(loadWeatherUseCase)
        return weatherInfoViewModel as T
    }
}
