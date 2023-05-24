package com.example.androidsemester4.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsemester4.Container
import com.example.androidsemester4.data.WeatherResponce
import kotlinx.coroutines.launch

class WeatherInfoViewModel : ViewModel() {
    private val _resultApi = MutableLiveData<WeatherResponce>()
    val resultApi: LiveData<WeatherResponce>
        get() = _resultApi

    fun getApi(query: String) {
        viewModelScope.launch {
            _resultApi.value = Container.weatherApi.getWeather(query)
        }
    }
}
