package com.example.androidsemester4.data


interface LoadCityRepository {
    suspend fun getWeather(cityName: String): WeatherResponce
    companion object{
        fun getInstance():LoadCityRepository{
            return LoadCityRepositoryImpl
        }
    }
}
