package com.example.androidsemester4.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsemester4.ui.Model.City
import com.example.androidsemester4.ui.Model.CityRepository
import kotlinx.coroutines.launch

class SearchWeatherViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>>
        get() = _cities

    fun getCities(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _isLoading.value = true
            val listCities = CityRepository.getNearCity(latitude, longitude)
            _cities.value = listCities
            _isLoading.value = false
        }
    }
}
