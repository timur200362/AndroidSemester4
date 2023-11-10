package com.example.androidsemester4.ui.mvi.secondPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androidsemester4.databinding.FragmentWeatherinfoBinding
import com.example.androidsemester4.ui.ComposeTest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class WeatherInfoComposeFragmentMVI : Fragment() {
    private var binding: FragmentWeatherinfoBinding? = null
    private lateinit var viewModel: WeatherInfoViewModelMVI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[WeatherInfoViewModelMVI::class.java]
        lifecycleScope.launch {
            viewModel.state.collect {
                val weatherUi=it.weatherUi
                showTemp(weatherUi.temp)
                showFeelsLike(weatherUi.feelsLike)
                showMaxMinTemp(weatherUi.tempMax, weatherUi.tempMin)
                showDesription(weatherUi.description)
                showWeatherIcon(weatherUi.icon)
                showSunset(weatherUi.sunset)
                showSunrise(weatherUi.sunrise)
                binding?.tvWind?.run {
                    text = "Ветер: ${weatherUi.windUi.direction}, ${weatherUi.windUi.speed} м/с"
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ComposeTest(arguments?.getString("cityName") ?:"", viewModel.state.collectAsStateWithLifecycle().value)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("cityName")?.let { loadWeather(it) }
    }

    private fun loadWeather(query: String) {
        viewModel.loadWeather(query)
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
        fun getInstance(bundle: Bundle?): WeatherInfoComposeFragmentMVI {
            val weatherInfoFragment = WeatherInfoComposeFragmentMVI()
            weatherInfoFragment.arguments = bundle
            return weatherInfoFragment
        }
    }

}