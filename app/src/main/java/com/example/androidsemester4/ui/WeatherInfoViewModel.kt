package com.example.androidsemester4.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsemester4.App
import com.example.androidsemester4.domain.LoadWeatherUseCase
import com.example.androidsemester4.domain.Weather
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherInfoViewModel : ViewModel() {
    @Inject
    lateinit var loadWeatherUseCase: LoadWeatherUseCase

    private val _resultApi = MutableLiveData<Weather>()
    val resultApi: LiveData<Weather>
        get() = _resultApi

    fun getApi(query: String) {
        viewModelScope.launch {
            App().appComponent.inject(this@WeatherInfoViewModel)
            _resultApi.value = loadWeatherUseCase.execute(query)
        }
    }
}
