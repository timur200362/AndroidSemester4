package com.example.androidsemester4.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.androidsemester4.R
import com.example.androidsemester4.databinding.FragmentSearchweatherBinding
import com.example.androidsemester4.hideKeyboard
import com.google.android.gms.location.*
import kotlinx.coroutines.launch
import timber.log.Timber


class SearchWeatherFragment: Fragment(R.layout.fragment_searchweather) {
    private lateinit var viewModel: SearchWeatherViewModel
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var binding: FragmentSearchweatherBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchWeatherViewModel::class.java]
        viewModel.isLoading.observe(this){
            showLoading(it)
        }
        viewModel.cities.observe(this){
            binding?.cityList?.adapter=CityAdapter(
                it,
                glide = Glide.with(this@SearchWeatherFragment)){
                loadWeather(it.name)
            }
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                viewLifecycleOwner.lifecycleScope.launch{
                    p0.lastLocation?.run{
                        viewModel.getCities(latitude, longitude)
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
            .addToBackStack(null)
            .commit()
    }

    private fun showLoading(isShow: Boolean) {
        binding?.progress?.isVisible = isShow
    }
}
