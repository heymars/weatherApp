package com.sujeet.weatherapp.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sujeet.weatherapp.BaseApplication
import com.sujeet.weatherapp.data.remote.ApiService
import com.sujeet.weatherapp.data.remote.RemoteDataSource
import com.sujeet.weatherapp.data.repositories.DataRepository
import com.sujeet.weatherapp.data.repositories.DataRepositoryImpl
import com.sujeet.weatherapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Sujeet on 05/05/22.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    fun buildOkHttpClient(@ApplicationContext appContext: Context): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .build()
                chain.proceed(request)
            }
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext appContext: Context): OkHttpClient = buildOkHttpClient(appContext)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
    ) : DataRepository =
        DataRepositoryImpl(remoteDataSource)
}