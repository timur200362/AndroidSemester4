package com.example.androidsemester4.ui.mvi

sealed class MainScreenUiEvent : UiEvent {
    data class LoadCities(val location:Location): MainScreenUiEvent()
    object GetLocation:MainScreenUiEvent()
}
data class Location(
    val latitude: Double,
    val longitude: Double
)