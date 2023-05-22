package com.example.androidsemester4.domain

import com.example.androidsemester4.data.CityRepository
import com.example.androidsemester4.ui.model.City

class GetNearCitiesUseCase {
    suspend fun execute(latitude: Double, longitude: Double): List<City> {
        return CityRepository.getNearCity(latitude, longitude)
    }
}
