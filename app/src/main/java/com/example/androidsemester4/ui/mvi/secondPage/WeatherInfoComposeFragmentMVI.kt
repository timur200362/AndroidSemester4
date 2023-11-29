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
                showWeatherIcon(weatherUi.icon)
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

    private fun showWeatherIcon(id: String) {
        binding?.ivIcon?.load("https://openweathermap.org/img/w/$id.png") {
            crossfade(true)
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