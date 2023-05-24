package com.example.androidsemester4.di

import com.example.androidsemester4.domain.GetNearCitiesUseCase
import com.example.androidsemester4.domain.LoadWeatherUseCase
import com.example.androidsemester4.ui.*
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    //SearchWeatherFragment
    fun injectSearchWeather(fragment: SearchWeatherFragment)
    fun injectSearchWeatherFactory(factory: SearchWeatherViewModelFactory)
    fun injectSearchWeatherViewModel(viewModel: SearchWeatherViewModel)
    fun injectGetNearCities(useCase: GetNearCitiesUseCase)

    //WeatherInfoFragment
    fun injectWeatherInfo(fragment: WeatherInfoFragment)
    fun injectWeatherInfoFactory(factory: WeatherInfoViewModelFactory)
    fun injectWeatherInfoViewModel(viewModel: WeatherInfoViewModel)
    fun injectLoadWeather(useCase: LoadWeatherUseCase)
}
