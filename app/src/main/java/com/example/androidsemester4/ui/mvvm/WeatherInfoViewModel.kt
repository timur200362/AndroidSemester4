package com.example.androidsemester4.ui.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsemester4.domain.LoadWeatherUseCase
import com.example.androidsemester4.domain.WeatherUi
import kotlinx.coroutines.launch

class WeatherInfoViewModel : ViewModel() {
    private val _resultApi = MutableLiveData<WeatherUi>()
    val resultApi: LiveData<WeatherUi>
        get() = _resultApi

    fun getApi(query: String) {
        viewModelScope.launch {
            _resultApi.value = LoadWeatherUseCase().execute(query)
        }
    }
}
