package com.example.androidsemester4.ui.mvi.mviRealisation

interface TimeCapsule<S : UiState> {
    fun addState(state: S)
    fun selectState(position: Int)
    fun getStates(): List<S>
}