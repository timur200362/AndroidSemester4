package com.example.androidsemester4.data.response

import com.example.androidsemester4.data.CityResponse
import com.example.androidsemester4.data.WeatherResponce
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
    ): Single<WeatherResponce>

    @GET("find")
    fun getCities(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("cnt") count: Int
    ): Single<CityResponse>
}
