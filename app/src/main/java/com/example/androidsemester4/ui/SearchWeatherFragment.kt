package com.example.androidsemester4.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.androidsemester4.R
import com.example.androidsemester4.databinding.FragmentSearchweatherBinding
import com.example.androidsemester4.ui.model.CityAdapter
import com.example.androidsemester4.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Flowables
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SearchWeatherFragment : Fragment(R.layout.fragment_searchweather) {
    private lateinit var viewModel: SearchWeatherViewModel
    private var binding: FragmentSearchweatherBinding? = null
    private var searchDisposable: Disposable?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this
        )[SearchWeatherViewModel::class.java]
        viewModel.getLocation()
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
            searchDisposable=etCity.observeQuery()//RxJava
                .filter{it.length>2}
                .debounce (500L, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onNext = {
                    //viewModel.onLoadClick(etCity.text.toString())
                },
                onError = {
                    Log.e("Error", it.toString())
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchDisposable?.dispose()
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

    private fun EditText.observeQuery() =//RxJava
        Flowables.create<String>(mode=BackpressureStrategy.LATEST){emitter->
            addTextChangedListener {
                emitter.onNext(it.toString())
            }
        }
}
