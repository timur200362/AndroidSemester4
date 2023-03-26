package com.example.androidsemester4.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.androidsemester4.Container
import com.example.androidsemester4.R
import com.example.androidsemester4.databinding.FragmentSearchweatherBinding
import com.example.androidsemester4.hideKeyboard
import com.example.androidsemester4.ui.Model.CityRepository

class SearchWeatherFragment: Fragment(R.layout.fragment_searchweather) {
    private var binding: FragmentSearchweatherBinding?=null
    private var adapter:CityAdapter?=null
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
            adapter=CityAdapter(
                CityRepository.games,
                glide = Glide.with(this@SearchWeatherFragment)){
                loadWeather(etCity.text.toString())
            }
            cityList.adapter=adapter
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