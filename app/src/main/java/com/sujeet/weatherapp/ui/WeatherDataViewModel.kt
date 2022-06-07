package com.sujeet.weatherapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sujeet.weatherapp.data.repositories.DataRepository
import com.sujeet.weatherapp.model.Hour
import com.sujeet.weatherapp.model.WeatherDataResponseModel
import com.sujeet.weatherapp.utils.Resource
import com.sujeet.weatherapp.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Sujeet on 06/05/22.
 */
@HiltViewModel
class WeatherDataViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {
    val weatherDataLiveData: MutableLiveData<Resource<WeatherDataResponseModel>> = MutableLiveData()
    val weatherForecastDataLiveData: MutableLiveData<WeatherDataResponseModel> = MutableLiveData()
    val weatherHourDataLiveData: MutableLiveData<Hour> = MutableLiveData()
    fun getWeatherDataByCityName(cityName: String) {
        weatherDataLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        repository.getWeatherDataByCityName(cityName).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribe(object : DisposableObserver<WeatherDataResponseModel>() {
                override fun onNext(weatherForecastResponseModel: WeatherDataResponseModel) {
                    weatherDataLiveData.postValue(
                        Resource(
                            ResourceState.SUCCESS,
                            weatherForecastResponseModel,
                            null
                        )
                    )
                    weatherForecastDataLiveData.value = weatherForecastResponseModel
                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {
                    weatherDataLiveData.postValue(
                        Resource(
                            ResourceState.ERROR,
                            null,
                            e.message
                        )
                    )
                    Timber.d("--------${e.message}----------")
                }
            })
    }
}