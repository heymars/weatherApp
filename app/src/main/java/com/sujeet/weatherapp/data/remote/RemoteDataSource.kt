package com.sujeet.weatherapp.data.remote

import com.sujeet.weatherapp.model.WeatherDataResponseModel
import com.sujeet.weatherapp.utils.Constants
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * Created by Sujeet on 05/05/22.
 */
class RemoteDataSource  @Inject constructor(private val apiService: ApiService) {
    fun getWeatherDataByCityName(cityName:String): Observable<WeatherDataResponseModel> {
        return apiService.getWeatherDataByCityName(cityName, Constants.APP_ID, "no", "no", 1)
    }
}