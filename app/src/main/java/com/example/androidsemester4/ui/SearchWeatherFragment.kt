package com.example.androidsemester4.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.androidsemester4.R
import com.example.androidsemester4.databinding.FragmentSearchweatherBinding
import com.example.androidsemester4.hideKeyboard
import com.example.androidsemester4.ui.Model.CityRepository
import com.google.android.gms.location.*
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchWeatherFragment: Fragment(R.layout.fragment_searchweather) {
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var binding: FragmentSearchweatherBinding?=null
    private var adapter:CityAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                Timber.tag("").i(p0.lastLocation?.toString())
                viewLifecycleOwner.lifecycleScope.launch{
                    p0.lastLocation?.run{
                        val listCities=CityRepository.getNearCity( latitude, longitude)
                        adapter=CityAdapter(
                            listCities,
                            glide = Glide.with(this@SearchWeatherFragment)){
                            loadWeather(it.name)
                        }
                        binding?.cityList?.adapter=adapter
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSearchweatherBinding.inflate(inflater,container,false).let{
            binding=it
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentSearchweatherBinding.bind(view)
        binding?.run {
            btnLoad.setOnClickListener{
                loadWeather(etCity.text.toString())
            }
            etCity.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    onLoadClick()
                }
                true
            }
        }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                    .setWaitForAccurateLocation(false)
                    .setMinUpdateIntervalMillis(500)
                    .setMaxUpdateDelayMillis(1000)
                    .build(),
                locationCallback,
                Looper.getMainLooper())
        }
    }


    private fun onLoadClick() {
        binding?.run {
            etCity.hideKeyboard()
            loadWeather(etCity.text.toString())
        }
    }

    private fun loadWeather(query:String){
        val bundle=Bundle()
        bundle.putString("cityName",query)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container,WeatherInfoFragment.getInstance(bundle),WeatherInfoFragment.WeatherInfoFragment_TAG)
            .commit()
    }
}