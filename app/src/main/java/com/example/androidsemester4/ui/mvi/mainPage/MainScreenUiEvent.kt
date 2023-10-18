package com.example.androidsemester4.ui.mvi.mainPage

import com.example.androidsemester4.ui.mvi.mviRealisation.UiEvent

sealed class MainScreenUiEvent : UiEvent {
    data class LoadCities(val location: Location): MainScreenUiEvent()
    object GetLocation: MainScreenUiEvent()
}
data class Location(
    val latitude: Double,
    val longitude: Double
)