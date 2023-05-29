package com.example.androidsemester4.data

import com.example.androidsemester4.data.response.WeatherApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LoadCityRepository @Inject constructor(private val weatherApi: WeatherApi) {
    fun getWeather(cityName: String): Single<WeatherResponce> = weatherApi.getWeather(cityName)
        .subscribeOn(Schedulers.io())
}
