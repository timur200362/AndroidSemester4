package com.example.androidsemester4.di


import com.example.androidsemester4.domain.GetNearCitiesUseCase
import com.example.androidsemester4.domain.LoadWeatherUseCase
import com.example.androidsemester4.ui.*
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    //SearchWeatherFragment
    fun inject(fragment: SearchWeatherFragment)
    fun inject(factory: SearchWeatherViewModelFactory)
    fun inject(viewModel: SearchWeatherViewModel)
    fun inject(useCase: GetNearCitiesUseCase)

    //WeatherInfoFragment
    fun inject(fragment: WeatherInfoFragment)
    fun inject(viewModel: WeatherInfoViewModel)
    fun inject(useCase: LoadWeatherUseCase)
}