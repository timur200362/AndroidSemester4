package com.example.androidsemester4.di

import android.app.Application
import com.example.androidsemester4.data.CityRepository
import com.example.androidsemester4.data.LoadCityRepository
import com.example.androidsemester4.domain.GetNearCitiesUseCase
import com.example.androidsemester4.domain.LoadWeatherUseCase
import com.example.androidsemester4.ui.SearchWeatherViewModel
import com.example.androidsemester4.ui.SearchWeatherViewModelFactory
import com.example.androidsemester4.ui.WeatherInfoViewModel
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(val application: Application) {
    @Provides
    fun provideFactory(
        application: Application
    ): SearchWeatherViewModelFactory = SearchWeatherViewModelFactory(application)

    @Provides
    fun provideApplication(): Application = application

    @Provides
    fun provideViewModel(application: Application): SearchWeatherViewModel =
        SearchWeatherViewModel(application)

    @Provides
    fun provideGetNearCitiesUseCase():GetNearCitiesUseCase=GetNearCitiesUseCase()

    @Provides
    fun provideCityRepository():CityRepository=CityRepository

    @Provides
    fun provideWeatherInfoViewModel():WeatherInfoViewModel=WeatherInfoViewModel()

    @Provides
    fun provideLoadWeatherUseCase():LoadWeatherUseCase= LoadWeatherUseCase()

    @Provides
    fun provideLoadCityRepository():LoadCityRepository=LoadCityRepository()
}
