package com.example.androidsemester4.ui

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsemester4.domain.GetNearCitiesUseCase
import com.example.androidsemester4.ui.model.City
import com.google.android.gms.location.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class SearchWeatherViewModel @Inject constructor(
    private val application: Application,
    private val getNearCitiesUseCase: GetNearCitiesUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>>
        get() = _cities

    var disposable: CompositeDisposable = CompositeDisposable()//RxJava

    fun getCities(latitude: Double, longitude: Double) {
        disposable += getNearCitiesUseCase.execute(latitude, longitude)//RxJava
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _isLoading.value = true }
            .doAfterTerminate { _isLoading.value = false }
            .subscribeBy(onSuccess = { cities ->
                _cities.value = cities
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()//RxJava
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
