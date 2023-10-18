package com.example.androidsemester4.domain

import com.example.androidsemester4.data.LoadCityRepository

class LoadWeatherUseCase {

    suspend fun execute(cityName: String): WeatherUi {
        LoadCityRepository.getInstance().getWeather(cityName).run {
            return WeatherUi(
                windUi = WindUi(getWindInfo(wind.deg), wind.speed),
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

data class WindUi(
    val direction: String = "",
    val speed: Double = 0.0
)

data class WeatherUi(
    val windUi: WindUi = WindUi(),
    val feelsLike: Double = 0.0,
    val humidity: Int = 0,
    val temp: Double = 0.0,
    val tempMax: Double = 0.0,
    val tempMin: Double = 0.0,
    val description: String = "",
    val icon: String = "",
    val sunrise: Int = 0,
    val sunset: Int = 0
)
