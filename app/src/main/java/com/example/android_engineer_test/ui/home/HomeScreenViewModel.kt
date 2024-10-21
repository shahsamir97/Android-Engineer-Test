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

class HomeScreenViewModel(private val homeScreenRepo: HomeScreenRepo) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Init)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    private val _recentSearches = MutableStateFlow<List<String>>(listOf())
    val recentSearches: StateFlow<List<String>> = _recentSearches

    private lateinit var previousData: WeatherDataUi

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
                        temp = mainWeatherInfo.temp.toString(),
                        minTemp = mainWeatherInfo.temp_min.toString(),
                        maxTemp = mainWeatherInfo.temp_max.toString(),
                        feelsLike = mainWeatherInfo.feels_like.toString(),
                        iconUrl = result.weather?.let { createImageLink(it[0].icon.toString()) } ?: ""
                    )

                    previousData = weatherDataUi
                    _homeUiState.update { HomeUiState.Success(weatherDataUi) }
                } ?: throw IllegalArgumentException()
            }
            catch (exception: Exception) {
                exception.printStackTrace()

                exception.message?.let { errorMessage ->
                    _homeUiState.update { HomeUiState.Error(errorMessage, previousData) }
                }
            }
        }
    }
}

sealed interface HomeUiState {
    data object Init: HomeUiState
    data object Loading: HomeUiState
    data class Success(val weatherDataUi: WeatherDataUi): HomeUiState
    data class Error(val errorMessage: String, val previousData: WeatherDataUi): HomeUiState
}