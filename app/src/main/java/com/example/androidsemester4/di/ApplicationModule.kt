package com.example.androidsemester4.di

import android.app.Application
import com.example.androidsemester4.data.CityRepository
import com.example.androidsemester4.data.LoadCityRepository
import com.example.androidsemester4.data.response.WeatherApi
import com.example.androidsemester4.domain.GetNearCitiesUseCase
import com.example.androidsemester4.domain.LoadWeatherUseCase
import com.example.androidsemester4.ui.SearchWeatherViewModel
import com.example.androidsemester4.ui.SearchWeatherViewModelFactory
import com.example.androidsemester4.ui.WeatherInfoViewModel
import com.example.androidsemester4.ui.WeatherInfoViewModelFactory
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class])
class ApplicationModule(val application: Application) {
    @Provides
    fun provideFactory(
        application: Application,
        getNearCitiesUseCase: GetNearCitiesUseCase
    ): SearchWeatherViewModelFactory =
        SearchWeatherViewModelFactory(application, getNearCitiesUseCase)

    @Provides
    fun provideApplication(): Application = application

    @Provides
    fun provideViewModel(
        application: Application,
        getNearCitiesUseCase: GetNearCitiesUseCase
    ): SearchWeatherViewModel =
        SearchWeatherViewModel(application, getNearCitiesUseCase)

    @Provides
    fun provideGetNearCitiesUseCase(cityRepository: CityRepository): GetNearCitiesUseCase =
        GetNearCitiesUseCase(cityRepository)

    @Provides
    fun provideCityRepository(weatherApi: WeatherApi): CityRepository = CityRepository(weatherApi)

    @Provides
    fun provideWeatherInfoViewModelFactory(
        loadWeatherUseCase: LoadWeatherUseCase
    ): WeatherInfoViewModelFactory = WeatherInfoViewModelFactory(loadWeatherUseCase)

    @Provides
    fun provideWeatherInfoViewModel(loadWeatherUseCase: LoadWeatherUseCase): WeatherInfoViewModel =
        WeatherInfoViewModel(loadWeatherUseCase)

    @Provides
    fun provideLoadWeatherUseCase(loadCityRepository: LoadCityRepository): LoadWeatherUseCase =
        LoadWeatherUseCase(loadCityRepository)

    @Provides
    fun provideLoadCityRepository(weatherApi: WeatherApi): LoadCityRepository =
        LoadCityRepository(weatherApi)
}
