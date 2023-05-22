package com.example.androidsemester4.data

import com.example.androidsemester4.data.response.WeatherApi
import com.example.androidsemester4.ui.model.City

class CityRepository(private val weatherApi: WeatherApi) {
    suspend fun getNearCity(latitude: Double, longitude: Double): List<City> {
        val response = weatherApi.getCities(latitude, longitude, 10)
        return response.list.map { City(it.name, it.weather[0].icon) }
    }
}
