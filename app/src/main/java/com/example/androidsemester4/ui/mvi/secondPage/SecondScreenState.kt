package com.example.androidsemester4.ui.mvi.secondPage

import com.example.androidsemester4.domain.WeatherUi
import com.example.androidsemester4.ui.mvi.mviRealisation.UiState

data class SecondScreenState(
    val weatherUi: WeatherUi
):UiState {
    companion object{
        fun initial()=SecondScreenState(
            weatherUi = WeatherUi()
        )
    }
}