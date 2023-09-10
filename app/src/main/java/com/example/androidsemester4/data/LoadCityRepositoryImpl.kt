package com.example.androidsemester4.data

internal object LoadCityRepositoryImpl:LoadCityRepository {
    override suspend fun getWeather(cityName: String): WeatherResponce {
        return DataContainer.weatherApi.getWeather(cityName)
    }
}