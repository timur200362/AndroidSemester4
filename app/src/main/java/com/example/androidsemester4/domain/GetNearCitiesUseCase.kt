package com.example.androidsemester4.domain

import com.example.androidsemester4.data.CityRepository
import com.example.androidsemester4.ui.model.City
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetNearCitiesUseCase @Inject constructor(private val cityRepository: CityRepository) {
    fun execute(latitude: Double, longitude: Double): Single<List<City>> = cityRepository.getNearCity(latitude, longitude)
}
