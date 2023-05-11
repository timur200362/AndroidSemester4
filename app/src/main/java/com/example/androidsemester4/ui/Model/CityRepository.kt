package com.example.androidsemester4.ui.Model

import com.example.androidsemester4.Container

object CityRepository {
    suspend fun getNearCity(latitude: Double, longitude: Double): List<City> {
        val response = Container.weatherApi.getCities(latitude, longitude, 10)
        return response.list.map { City(it.name) }
    }
}
