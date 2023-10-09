package com.example.androidsemester4.ui.mvi

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewModelScope
import com.example.androidsemester4.domain.GetLocationUseCase
import com.example.androidsemester4.domain.GetNearCitiesUseCase
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelMVI(
    private val application: Application,
) : BaseViewModel<MainScreenState, MainScreenUiEvent>() {

    private val reducer = MainReducer(MainScreenState.initial(), application)

    override val state: StateFlow<MainScreenState>
        get() = reducer.state

    val timeMachine: TimeCapsule<MainScreenState>
        get() = reducer.timeCapsule

    init {
        viewModelScope.launch {
            sendEvent(MainScreenUiEvent.GetLocation)
        }
    }

    private fun sendEvent(event: MainScreenUiEvent) {
        viewModelScope.launch{
            reducer.sendEvent(event)
        }
    }

    fun loadNearCities(){
        sendEvent(MainScreenUiEvent.GetLocation)
    }

    private class MainReducer(initial: MainScreenState, val context:Context) : Reducer<MainScreenState, MainScreenUiEvent>(initial) {
        override suspend fun reduce(oldState: MainScreenState, event: MainScreenUiEvent) {
            when (event) {
                is MainScreenUiEvent.LoadCities -> {
                    setState(oldState.copy(isLoading = true))
                    val listCities = GetNearCitiesUseCase().execute(event.location.latitude,event.location.longitude)
                    setState(oldState.copy(isLoading = false, data = listCities))//newState
                }
                is MainScreenUiEvent.GetLocation -> {
                    setState(oldState.copy(determinateLocation = true))
                    val location = GetLocationUseCase(context).getLocation()
                    val newState = setState(oldState.copy(determinateLocation = false))
                    if (location != null) {
                        reduce(newState,MainScreenUiEvent.LoadCities(location))
                    }
                    else{
                        Log.e("test","null")
                    }
                }
            }
        }
    }
}