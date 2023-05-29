package com.example.androidsemester4.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsemester4.domain.LoadWeatherUseCase
import com.example.androidsemester4.domain.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(private val loadWeatherUseCase: LoadWeatherUseCase) :
    ViewModel() {

    private val _resultApi = MutableLiveData<Weather>()
    val resultApi: LiveData<Weather>
        get() = _resultApi

    var disposable: CompositeDisposable = CompositeDisposable()//RxJava

    fun getApi(query: String) {
        disposable += loadWeatherUseCase.execute(query)//RxJava
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = { resultApi ->
                _resultApi.value = resultApi
            })
    }
}
