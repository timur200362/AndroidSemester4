package com.example.androidsemester4.domain

import com.example.androidsemester4.App
import com.example.androidsemester4.data.CityRepository
import com.example.androidsemester4.ui.Model.City
import javax.inject.Inject

class GetNearCitiesUseCase {
    @Inject
    lateinit var cityRepository: CityRepository

    suspend fun execute(latitude: Double, longitude: Double): List<City> {
        App().appComponent.inject(this@GetNearCitiesUseCase)
        return cityRepository.getNearCity(latitude, longitude)
    }
}
