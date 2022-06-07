package com.sujeet.weatherapp.data.repositories

import com.sujeet.weatherapp.model.WeatherDataResponseModel
import io.reactivex.rxjava3.core.Observable

/**
 * Created by Sujeet on 05/05/22.
 */
interface DataRepository {
    fun getWeatherDataByCityName(cityName: String): Observable<WeatherDataResponseModel>
}