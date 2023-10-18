package com.example.androidsemester4.domain

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.example.androidsemester4.ui.mvi.mainPage.Location
import com.google.android.gms.location.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GetLocationUseCase(private val context: Context) {
    suspend fun getLocation(): Location? {
        return suspendCoroutine {
            val fusedLocation =
                LocationServices.getFusedLocationProviderClient(context)
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    p0.lastLocation?.run {
                        it.resume(Location(latitude,longitude))
                    }
                }
            }
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                it.resume(null)
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
}