package com.example.androidsemester4.ui.mvi

sealed class MainScreenUiEvent : UiEvent {
    object LoadCities:MainScreenUiEvent()
}