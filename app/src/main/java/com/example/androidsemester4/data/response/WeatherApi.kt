package com.example.androidsemester4.data.response

import com.example.androidsemester4.API_KEY
import com.example.androidsemester4.data.WeatherResponce
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") city:String,
        @Query("units") units:String="metric",
        @Query("appid") key: String= API_KEY
    ): WeatherResponce

    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") long: Double
    ):WeatherResponce
}
