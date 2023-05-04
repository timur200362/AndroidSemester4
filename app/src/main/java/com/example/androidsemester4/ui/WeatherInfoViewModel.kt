package com.example.androidsemester4.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsemester4.domain.LoadWeatherUseCase
import com.example.androidsemester4.domain.Weather
import kotlinx.coroutines.launch

class WeatherInfoViewModel(val loadWeatherUseCase: LoadWeatherUseCase) : ViewModel() {

    private val _resultApi = MutableLiveData<Weather>()
    val resultApi: LiveData<Weather>
        get() = _resultApi

    fun getApi(query: String) {
        viewModelScope.launch {
            _resultApi.value = loadWeatherUseCase.execute(query)
        }
    }
}
