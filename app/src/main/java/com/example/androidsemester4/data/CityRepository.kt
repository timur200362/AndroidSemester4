package com.example.androidsemester4.data

import com.example.androidsemester4.ui.model.City

interface CityRepository {
    suspend fun getNearCity(latitude: Double, longitude: Double): List<City>
    companion object{
        fun getInstance():CityRepository{
            return CityRepositoryImpl
        }
    }
}