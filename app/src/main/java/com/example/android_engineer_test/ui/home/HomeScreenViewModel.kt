package com.example.android_engineer_test.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_engineer_test.model.WeatherDataUi
import com.example.android_engineer_test.utils.createImageLink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeScreenViewModel(private val homeScreenRepo: HomeScreenRepo) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Init)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    private lateinit var _previousData: WeatherDataUi
    private val _recentSearchList  = ArrayList<String>()

    init {
        searchWeather("Dhaka")
    }

    fun searchWeather(cityName: String) {
        _homeUiState.update { HomeUiState.Loading }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = homeScreenRepo.fetchWeatherDataByCityName(cityName)

                result.main?.let { mainWeatherInfo ->
                    val weatherDataUi = WeatherDataUi(
                        temp = mainWeatherInfo.temp?.toInt().toString(),
                        minTemp = mainWeatherInfo.temp_min?.toInt().toString(),
                        maxTemp = mainWeatherInfo.temp_max?.toInt().toString(),
                        feelsLike = mainWeatherInfo.feels_like?.toInt().toString(),
                        iconUrl = result.weather?.let { createImageLink(it[0].icon.toString()) } ?: ""
                    )

                    _previousData = weatherDataUi
                    _recentSearchList.add(cityName)
                    _homeUiState.update { HomeUiState.Success(weatherDataUi, _recentSearchList) }
                } ?: throw IllegalArgumentException()
            }
            catch (exception: Exception) {
                exception.printStackTrace()

                exception.message?.let { errorMessage ->
                    _homeUiState.update { HomeUiState.Error(errorMessage, _previousData, _recentSearchList) }
                }
            }
        }
    }

    fun clearRecentSearches() {
        runBlocking {
            _recentSearchList.clear()
            _homeUiState.value =  HomeUiState.Success(_previousData, _recentSearchList)
        }
    }
}

sealed interface HomeUiState {
    data object Init: HomeUiState
    data object Loading: HomeUiState
    data class Success(val weatherDataUi: WeatherDataUi, val recentSearches: List<String>): HomeUiState
    data class Error(val errorMessage: String, val previousData: WeatherDataUi, val recentSearches: List<String>): HomeUiState
}