package com.example.androidsemester4.domain

import com.example.androidsemester4.data.LoadCityRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LoadWeatherUseCase @Inject constructor(private val loadCityRepository: LoadCityRepository) {

    fun execute(cityName: String): Single<Weather> {
        loadCityRepository.getWeather(cityName).run {
            return Weather(
                wind = Wind(getWindInfo(wind.deg), wind.speed),
                feelsLike = main.feelsLike,
                humidity = main.humidity,
                temp = main.temp,
                tempMax = main.tempMax,
                tempMin = main.tempMin,
                description = weather[0].description,
                icon = weather[0].icon,
                sunrise = sys.sunrise,
                sunset = sys.sunset
            )
        }
    }

    private fun getWindInfo(deg: Int): String {
        return when (deg) {
            in 0..10 -> "C"
            in 350..360 -> "C"
            in 260..280 -> "З"
            in 170..190 -> "Ю"
            in 80..100 -> "В"
            in 281..349 -> "СЗ"
            in 11..79 -> "CВ"
            in 190..259 -> "ЮЗ"
            in 101..169 -> "ЮВ"
            else -> "ex"
        }
    }
}

data class Wind(
    val direction: String,
    val speed: Double
)

data class Weather(
    val wind: Wind,
    val feelsLike: Double,
    val humidity: Int,
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double,
    val description: String,
    val icon: String,
    val sunrise: Int,
    val sunset: Int
)
