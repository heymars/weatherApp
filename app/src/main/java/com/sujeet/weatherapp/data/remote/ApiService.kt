package com.sujeet.weatherapp.data.remote

import com.sujeet.weatherapp.model.WeatherDataResponseModel
import com.sujeet.weatherapp.utils.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Sujeet on 05/05/22.
 */
interface ApiService {
    @GET(Constants.WEATHER_FORECAST)
    fun getWeatherDataByCityName(@Query("q") cityName: String ,
                                 @Query("key") appId: String,
                                 @Query("aqi") aqi: String,
                                 @Query("alerts") alerts: String,
    @Query("days") days : Int, ): Observable<WeatherDataResponseModel>
}