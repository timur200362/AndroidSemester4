package com.example.androidsemester4.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.androidsemester4.App
import com.example.androidsemester4.R
import com.example.androidsemester4.databinding.FragmentSearchweatherBinding
import com.example.androidsemester4.utils.hideKeyboard
import javax.inject.Inject

class SearchWeatherFragment : Fragment(R.layout.fragment_searchweather) {
    private lateinit var viewModel: SearchWeatherViewModel
    private var binding: FragmentSearchweatherBinding? = null

    @Inject
    lateinit var searchWeatherViewModelFactory: SearchWeatherViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as App).appComponent.injectSearchWeather(this)//dagger2
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            searchWeatherViewModelFactory
        )[SearchWeatherViewModel::class.java]
        viewModel.getLocation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSearchweatherBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchweatherBinding.bind(view)
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        viewModel.cities.observe(viewLifecycleOwner) {
            binding?.cityList?.adapter = CityAdapter(
                it,
                glide = Glide.with(this@SearchWeatherFragment)
            ) {
                loadWeather(it.name)
            }
        }
        binding?.run {
            btnLoad.setOnClickListener {
                loadWeather(etCity.text.toString())
            }
            etCity.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    onLoadClick()
                }
                true
            }
        }
    }


    private fun onLoadClick() {
        binding?.run {
            etCity.hideKeyboard()
            loadWeather(etCity.text.toString())
        }
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
