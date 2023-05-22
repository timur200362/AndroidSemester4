package com.example.androidsemester4.ui

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsemester4.domain.GetNearCitiesUseCase
import com.example.androidsemester4.ui.model.City
import com.google.android.gms.location.*
import kotlinx.coroutines.launch

class SearchWeatherViewModel(val application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>>
        get() = _cities

    fun getCities(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _isLoading.value = true
            val listCities = GetNearCitiesUseCase().execute(latitude, longitude)
            _cities.value = listCities
            _isLoading.value = false
        }
    }

    //с factory
    fun getLocation() {
        val fusedLocation =
            LocationServices.getFusedLocationProviderClient(application.applicationContext)
        //без liveData, потому что 1 раз инициализируем. Не участвует в отображении данных
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                p0.lastLocation?.run {
                    getCities(latitude, longitude)
                }
            }
        }
        if (ActivityCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocation.requestLocationUpdates(
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(500)
                .setMaxUpdateDelayMillis(1000)
                .build(),
            locationCallback,
            Looper.getMainLooper()
        )
    }
}
