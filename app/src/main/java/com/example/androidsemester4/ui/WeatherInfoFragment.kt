package com.example.androidsemester4.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.androidsemester4.Container
import com.example.androidsemester4.R
import com.example.androidsemester4.databinding.FragmentWeatherinfoBinding
import com.example.androidsemester4.showSnackbar
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class WeatherInfoFragment:Fragment(R.layout.fragment_weatherinfo) {
    private var binding: FragmentWeatherinfoBinding?=null

    private val api = Container.weatherApi

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentWeatherinfoBinding.bind(view)
        arguments?.getString("cityName")?.let { loadWeather(it) }
    }
    private fun loadWeather(query:String){
        lifecycleScope.launch{
            try{
                api.getWeather(query).also {
                    showTemp(it.main.temp)
                    showFeelsLike(it.main.feelsLike)
                    showMaxMinTemp(it.main.tempMax, it.main.tempMin)
                    showName(query)
                    it.weather.firstOrNull()?.also {
                        showWeatherIcon(it.icon)
                    }
                }
            }   catch (error:Throwable){
                showError(error)
            }
        }
    }


    private fun showError(error: Throwable){
        activity?.findViewById<View>(android.R.id.content)
            ?.showSnackbar(error.message ?: "Error")
    }

    private fun showTemp(temp:Double){
        binding?.tvTemp?.run {
            text="${temp.roundToInt()} C"
            //isVisible=true
        }
    }

    private fun showFeelsLike(feelsLike: Double){
        binding?.tvFeelsLike?.run {
            text="Ощущается как ${feelsLike.roundToInt()} C"
            //isVisible=true
        }
    }

    private fun showMaxMinTemp(tempMax:Double, tempMin: Double){
        binding?.tvTempMaxMin?.run {
            text="${tempMax.roundToInt()} C/${tempMin.roundToInt()} C"
            //isVisible=true
        }
    }

    private fun showWeatherIcon(id:String){
        binding?.ivIcon?.load("https://openweathermap.org/img/w/$id.png"){
            crossfade(true)
        }
    }

    private fun showName(name: String){
        binding?.tvName?.run {
            text=name
            //isVisible=true
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