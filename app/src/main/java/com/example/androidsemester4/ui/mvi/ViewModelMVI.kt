package com.example.androidsemester4.ui.mvi

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.androidsemester4.domain.GetNearCitiesUseCase
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelMVI(
    private val application: Application,
) : BaseViewModel<MainScreenState, MainScreenUiEvent>() {

    private val reducer = MainReducer(MainScreenState.initial())

    override val state: StateFlow<MainScreenState>
        get() = reducer.state

    val timeMachine: TimeCapsule<MainScreenState>
        get() = reducer.timeCapsule

    init {
        viewModelScope.launch {
            sendEvent(MainScreenUiEvent.LoadCities)
        }
    }

    private fun sendEvent(event: MainScreenUiEvent) {
        viewModelScope.launch{
            reducer.sendEvent(event)
        }
    }

    fun loadNearCities(){
        sendEvent(MainScreenUiEvent.LoadCities)
    }

    private class MainReducer(initial: MainScreenState) : Reducer<MainScreenState, MainScreenUiEvent>(initial) {
        override suspend fun reduce(oldState: MainScreenState, event: MainScreenUiEvent) {
            when (event) {
                is MainScreenUiEvent.LoadCities -> {
                    val newList=setState(oldState.copy(isLoading = true))
                    val listCities = GetNearCitiesUseCase().execute(55.7887, 49.1221)
                    setState(oldState.copy(isLoading = false, data = listCities))
                }
            }
        }
    }
}