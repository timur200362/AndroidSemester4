package com.example.androidsemester4.data


class LoadCityRepository {
    suspend fun getWeather(cityName: String): WeatherResponce {
        return DataContainer.weatherApi.getWeather(cityName)
    }
}
