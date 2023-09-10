package com.example.androidsemester4.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.androidsemester4.R
import com.example.androidsemester4.databinding.FragmentWeatherinfoBinding
import kotlin.math.roundToInt

class WeatherInfoFragment : Fragment(R.layout.fragment_weatherinfo) {
    private var binding: FragmentWeatherinfoBinding? = null
    private lateinit var viewModel: WeatherInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[WeatherInfoViewModel::class.java]
        viewModel.resultApi.observe(this) {
            showTemp(it.temp)
            showFeelsLike(it.feelsLike)
            showMaxMinTemp(it.tempMax, it.tempMin)
            showDesription(it.description)
            showWeatherIcon(it.icon)
            showHumidity(it.humidity)
            showSunset(it.sunset)
            showSunrise(it.sunrise)
            binding?.tvWind?.run {
                text = "Ветер: ${it.wind.direction}, ${it.wind.speed} м/с"
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

    companion object {
        const val WeatherInfoFragment_TAG = "WeatherInfoFragment_TAG"
        fun getInstance(bundle: Bundle?): WeatherInfoFragment {
            val weatherInfoFragment = WeatherInfoFragment()
            weatherInfoFragment.arguments = bundle
            return weatherInfoFragment
        }
    }

}
