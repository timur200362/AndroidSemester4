package com.example.androidsemester4.ui.mvi.secondPage

import androidx.lifecycle.viewModelScope
import com.example.androidsemester4.domain.LoadWeatherUseCase
import com.example.androidsemester4.ui.mvi.mviRealisation.BaseViewModel
import com.example.androidsemester4.ui.mvi.mviRealisation.Reducer
import com.example.androidsemester4.ui.mvi.mviRealisation.TimeCapsule
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherInfoViewModelMVI : BaseViewModel<SecondScreenState, SecondScreenUiEvent>() {
    private val reducer = SecondReducer(SecondScreenState.initial())

    override val state: StateFlow<SecondScreenState>
        get() = reducer.state

    val timeMachine: TimeCapsule<SecondScreenState>
        get() = reducer.timeCapsule

    private fun sendEvent(event: SecondScreenUiEvent) {
        viewModelScope.launch{
            reducer.sendEvent(event)
        }
    }

    fun loadWeather(cityName:String){
        sendEvent(SecondScreenUiEvent.LoadWeather(cityName))
    }

    private class SecondReducer(initial: SecondScreenState) : Reducer<SecondScreenState, SecondScreenUiEvent>(initial) {
        override suspend fun reduce(oldState: SecondScreenState, event: SecondScreenUiEvent) {
            when (event) {
                is SecondScreenUiEvent.LoadWeather->{
                    setState(oldState.copy())
                    val weatherUi=LoadWeatherUseCase().execute(event.cityName)
                    setState(oldState.copy(weatherUi))
                }
            }
        }
    }
}
