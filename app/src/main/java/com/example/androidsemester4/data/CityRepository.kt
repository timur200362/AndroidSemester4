package com.example.androidsemester4.data

import com.example.androidsemester4.data.response.WeatherApi
import com.example.androidsemester4.ui.model.City
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CityRepository @Inject constructor(private val weatherApi: WeatherApi) {
    fun getNearCity(latitude: Double, longitude: Double): Single<List<City>> {
        val response = weatherApi.getCities(latitude, longitude, 10)
        return response.list.map {
            City(it.name, it.weather[0].icon)
        }.subscribeOn(Schedulers.io())
    }
}
