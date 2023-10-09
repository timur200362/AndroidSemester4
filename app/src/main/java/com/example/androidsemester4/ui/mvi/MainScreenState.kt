package com.example.androidsemester4.ui.mvi

import com.example.androidsemester4.ui.model.City

data class MainScreenState(
    val isLoading: Boolean,
    val data: List<City>,
    val determinateLocation: Boolean
) : UiState {
    companion object {
        fun initial() = MainScreenState(
            isLoading = false,
            data = emptyList(),
            determinateLocation = true
        )
    }
}