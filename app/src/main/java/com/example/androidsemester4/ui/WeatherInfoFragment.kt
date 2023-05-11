package com.example.androidsemester4.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.androidsemester4.R
import com.example.androidsemester4.databinding.FragmentWeatherinfoBinding
import com.example.androidsemester4.showSnackbar
import kotlin.math.roundToInt

class WeatherInfoFragment : Fragment(R.layout.fragment_weatherinfo) {
    private var binding: FragmentWeatherinfoBinding? = null
    private lateinit var viewModel: WeatherInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[WeatherInfoViewModel::class.java]
        viewModel.resultApi.observe(this) {
            showTemp(it.main.temp)
            showFeelsLike(it.main.feelsLike)
            showMaxMinTemp(it.main.tempMax, it.main.tempMin)
            showDesription(it.weather[0].description)
            it.weather.firstOrNull()?.also {
                showWeatherIcon(it.icon)
            }
            showHumidity(it.main.humidity)
            showSunset(it.sys.sunset)
            showSunrise(it.sys.sunrise)
            binding?.tvWind?.run {
                text = "Ветер: ${getWindInfo(it.wind.deg)}, ${it.wind.speed} м/с"
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherinfoBinding.bind(view)
        arguments?.getString("cityName")?.let { loadWeather(it) }
    }

    private fun loadWeather(query: String) {
        viewModel.getApi(query)
        showName(query)
    }


    private fun showError(error: Throwable) {
        activity?.findViewById<View>(android.R.id.content)
            ?.showSnackbar(error.message ?: "Error")
    }

    private fun showTemp(temp: Double) {
        binding?.tvTemp?.run {
            text = "${temp.roundToInt()}°"
        }
    }

    private fun showFeelsLike(feelsLike: Double) {
        binding?.tvFeelsLike?.run {
            text = "Ощущается как ${feelsLike.roundToInt()}°"
        }
    }

    private fun showMaxMinTemp(tempMax: Double, tempMin: Double) {
        binding?.tvTempMaxMin?.run {
            text = "${tempMax.roundToInt()}°/${tempMin.roundToInt()}°"
        }
    }

    private fun showWeatherIcon(id: String) {
        binding?.ivIcon?.load("https://openweathermap.org/img/w/$id.png") {
            crossfade(true)
        }
    }

    private fun showName(name: String) {
        binding?.tvName?.run {
            text = name
        }
    }

    private fun showDesription(description: String) {
        binding?.tvDescription?.run {
            text = description
        }
    }

    private fun showHumidity(humidity: Int) {
        binding?.tvHumidity?.run {
            text = "Влажность: $humidity%"
        }
    }

    private fun showSunrise(sunrise: Int) {
        binding?.tvSunrise?.run {
            text = "Восход: $sunrise"
        }
    }

    private fun showSunset(sunset: Int) {
        binding?.tvSunset?.run {
            text = "Закат: $sunset"
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

    companion object {
        const val WeatherInfoFragment_TAG = "WeatherInfoFragment_TAG"
        fun getInstance(bundle: Bundle?): WeatherInfoFragment {
            val weatherInfoFragment = WeatherInfoFragment()
            weatherInfoFragment.arguments = bundle
            return weatherInfoFragment
        }
    }

}
