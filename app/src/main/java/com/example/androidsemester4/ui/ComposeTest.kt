package com.example.androidsemester4.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.androidsemester4.ui.mvi.secondPage.SecondScreenState


@Composable
fun ComposeTest(name:String,state:SecondScreenState) {
    Text("Город: ${name}")
    Text("Влажность: {${state.weatherUi.humidity}}")
}