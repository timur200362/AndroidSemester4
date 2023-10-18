package com.example.androidsemester4.ui.mvi.secondPage

import com.example.androidsemester4.ui.mvi.mviRealisation.UiEvent

sealed class SecondScreenUiEvent:UiEvent {
    data class LoadWeather(val cityName: String): SecondScreenUiEvent()
}