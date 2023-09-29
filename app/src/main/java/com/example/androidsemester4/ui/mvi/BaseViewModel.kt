package com.example.androidsemester4.ui.mvi

import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T : UiState, in E : UiEvent> : ViewModel() {
    abstract val state: Flow<T>
}