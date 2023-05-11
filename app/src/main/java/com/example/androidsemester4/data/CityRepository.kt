package com.example.androidsemester4.data

import com.example.androidsemester4.ui.Model.City

object CityRepository {
    suspend fun getNearCity(latitude: Double, longitude: Double): List<City> {
        val response = DataContainer.weatherApi.getCities(latitude, longitude, 10)
        return response.list.map { City(it.name, it.weather[0].icon) }
    }
}
