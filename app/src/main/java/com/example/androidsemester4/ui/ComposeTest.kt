package com.example.androidsemester4.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.androidsemester4.ui.mvi.secondPage.SecondScreenState


@Composable
fun ComposeTest(name:String,state:SecondScreenState) {
    Text("Город: ${name}", color = Color.White)
    Text("Влажность: ${state.weatherUi.humidity}", color = Color.White,modifier = Modifier.padding(top=15.dp))
    Text("${state.weatherUi.temp}°", color = Color.White, modifier = Modifier.padding(top=30.dp))
    Text("${state.weatherUi.tempMin}°/${state.weatherUi.tempMax}°",color = Color.White, modifier = Modifier.padding(top=45.dp))
    Text("Ощущается как: ${state.weatherUi.feelsLike}",color = Color.White, modifier = Modifier.padding(top=60.dp))
    Text("${state.weatherUi.description}", color = Color.White,modifier = Modifier.padding(top=75.dp))
    Text("Восход: ${state.weatherUi.sunrise}",color = Color.White, modifier = Modifier.padding(top=90.dp))
    Text("Закат: ${state.weatherUi.sunset}",color = Color.White, modifier = Modifier.padding(top=105.dp))
}