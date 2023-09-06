package com.example.androidsemester4.data

import com.example.androidsemester4.data.response.WeatherApi
import com.example.androidsemester4.ui.model.City
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CityRepository @Inject constructor(private val weatherApi: WeatherApi) {
    fun getNearCity(latitude: Double, longitude: Double): Single<List<City>> {
        return weatherApi.getCities(latitude, longitude, 10).map {
            it.list.map { City(it.name, it.weather[0].icon) }
        }
    }
}
