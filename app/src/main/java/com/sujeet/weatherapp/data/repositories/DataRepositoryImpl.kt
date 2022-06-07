package com.sujeet.weatherapp.data.repositories

import com.sujeet.weatherapp.data.remote.RemoteDataSource
import com.sujeet.weatherapp.model.WeatherDataResponseModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * Created by Sujeet on 05/05/22.
 */
class DataRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : DataRepository {
    override fun getWeatherDataByCityName(cityName: String): Observable<WeatherDataResponseModel> {
       return remoteDataSource.getWeatherDataByCityName(cityName)
    }
}