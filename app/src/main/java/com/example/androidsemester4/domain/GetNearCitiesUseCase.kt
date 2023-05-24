package com.example.androidsemester4.domain

import com.example.androidsemester4.data.CityRepository
import com.example.androidsemester4.ui.model.City
import javax.inject.Inject

class GetNearCitiesUseCase @Inject constructor(private val cityRepository: CityRepository) {
    suspend fun execute(latitude: Double, longitude: Double): List<City> {
        return cityRepository.getNearCity(latitude, longitude)
    }
}
