package com.example.androidsemester4.data

import com.example.androidsemester4.BuildConfig
import com.example.androidsemester4.data.interceptor.ApiKeyInterceptor
import com.example.androidsemester4.data.response.WeatherApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
const val API_KEY = "1c7f89ac71daa4aa8bb081c93922ec64"

object DataContainer {
    private val loggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ApiKeyInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    val weatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}
