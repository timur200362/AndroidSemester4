package com.example.androidsemester4.data

import com.example.androidsemester4.ui.Model.City

class LoadCityRepository {
    suspend fun getWeather(cityName:String) : WeatherResponce {
        return DataContainer.weatherApi.getWeather(cityName)
    }
}