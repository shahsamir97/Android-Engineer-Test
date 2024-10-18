package com.example.android_engineer_test.ui.home

import com.example.android_engineer_test.BuildConfig
import com.example.android_engineer_test.network.ApiServiceGenerator
import com.example.android_engineer_test.network.WeatherApiService

class HomeScreenRepo  {

    private val apiService = ApiServiceGenerator.createService(WeatherApiService::class.java)

    suspend fun fetchWeatherDataByCityName(cityName: String) =
        apiService.fetchWeatherData(
            appId = BuildConfig.APP_ID,
            unit = "metric",
            cityName = cityName
        )
}