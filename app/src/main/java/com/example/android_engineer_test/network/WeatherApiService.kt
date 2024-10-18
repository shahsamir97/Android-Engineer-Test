package com.example.android_engineer_test.network

import com.example.android_engineer_test.model.dto.WeatherDataDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun fetchWeatherData(
        @Query("appid") appId: String,
        @Query("units") unit: String,
        @Query("q") cityName: String
    ): WeatherDataDTO
}