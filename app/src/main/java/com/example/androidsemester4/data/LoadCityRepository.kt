package com.example.androidsemester4.data

import com.example.androidsemester4.data.response.WeatherApi
import javax.inject.Inject

class LoadCityRepository @Inject constructor(private val weatherApi: WeatherApi) {
    suspend fun getWeather(cityName: String): WeatherResponce {
        return weatherApi.getWeather(cityName)
    }
}
