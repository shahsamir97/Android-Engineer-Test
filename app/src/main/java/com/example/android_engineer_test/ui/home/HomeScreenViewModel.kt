package com.example.android_engineer_test.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_engineer_test.model.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val homeScreenRepo: HomeScreenRepo) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Init)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        searchWeather("Dhaka")
    }

    fun searchWeather(cityName: String) {
        _homeUiState.update { HomeUiState.Loading }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weatherData = homeScreenRepo.fetchWeatherDataByCityName(cityName)
                _homeUiState.update { HomeUiState.Success(weatherData) }
            } catch (exception: Exception) {
                exception.printStackTrace()
                exception.message?.let { errorMessage ->
                    _homeUiState.update { HomeUiState.Error(errorMessage) }
                }
            }
        }
    }
}

sealed interface HomeUiState {
    data object Init: HomeUiState
    data object Loading: HomeUiState
    data class Success(val weatherData: WeatherData): HomeUiState
    data class Error(val errorMessage: String): HomeUiState
}