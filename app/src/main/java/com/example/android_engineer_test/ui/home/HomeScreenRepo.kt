package com.example.android_engineer_test.ui.home

interface HomeScreenRepo {
    suspend fun fetchWeatherDataByCityName(cityName: String)
}

class HomeScreenRepoImpl : HomeScreenRepo {
    override suspend fun fetchWeatherDataByCityName(cityName: String) {
        TODO("Not yet implemented")
    }
}