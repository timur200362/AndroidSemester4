package com.example.androidsemester4.ui.mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.androidsemester4.R
import com.example.androidsemester4.databinding.FragmentSearchweatherBinding
import com.example.androidsemester4.ui.SearchWeatherViewModel
import com.example.androidsemester4.ui.SearchWeatherViewModelFactory
import com.example.androidsemester4.ui.WeatherInfoFragment
import com.example.androidsemester4.ui.model.CityAdapter
import com.example.androidsemester4.utils.hideKeyboard
import kotlinx.coroutines.launch

class SearchWeatherFragmentMVI : Fragment(R.layout.fragment_searchweather) {
    private lateinit var viewModel: ViewModelMVI
    private var binding: FragmentSearchweatherBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            SearchWeatherViewModelFactory(requireActivity().application)
        )[ViewModelMVI::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSearchweatherBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchweatherBinding.bind(view)
        lifecycleScope.launch {
            viewModel.state.collect {
                showLoading(it.isLoading)
                binding?.cityList?.adapter = CityAdapter(
                    it.data,
                    glide = Glide.with(this@SearchWeatherFragmentMVI)
                ) {
                    loadWeather(it.name)
                }
            }
        }
        viewModel.loadNearCities()
    }

    private fun loadWeather(query: String) {
        val bundle = Bundle()
        bundle.putString("cityName", query)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                WeatherInfoFragment.getInstance(bundle),
                WeatherInfoFragment.WeatherInfoFragment_TAG
            )
            .addToBackStack(null)
            .commit()
    }


    private fun showLoading(isShow: Boolean) {
        binding?.progress?.isVisible = isShow
    }
}
