package com.example.android_engineer_test.ui.home

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.android_engineer_test.R
import com.example.android_engineer_test.model.user.WeatherData
import com.example.android_engineer_test.ui.theme.AndroidEngineerTestTheme
import com.example.android_engineer_test.ui.widgets.InputDialog
import com.example.android_engineer_test.ui.widgets.VerticalSpacer
import com.example.android_engineer_test.utils.createImageLink

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = viewModel {
        HomeScreenViewModel(HomeScreenRepo())
    }
) {
    val homeUiState by homeScreenViewModel.homeUiState.collectAsState()

    HomeScreenContent(
        homeUiState = homeUiState,
        onClickSearchWeather = { cityName ->  homeScreenViewModel.searchWeather(cityName) }
    )
}

@Composable
fun HomeScreenContent(homeUiState: HomeUiState, onClickSearchWeather: (cityName: String) -> Unit) {
    val context = LocalContext.current
    var showInputDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(containerColor = MaterialTheme.colorScheme.surfaceContainer) { contentPadding ->

        when(homeUiState) {
            HomeUiState.Init -> {}

            HomeUiState.Loading -> {
                CircularProgressIndicator()
            }

            is HomeUiState.Success -> {
                WeatherContentCard(
                    contentPadding = contentPadding,
                    weatherData = homeUiState.weatherData,
                    onClickSearchWeather = { showInputDialog = true }
                )
            }

            is HomeUiState.Error -> {
                Toast.makeText(context, homeUiState.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        if (showInputDialog) {
            InputDialog(
                title = stringResource(R.string.enter_a_location),
                onClick = { inputValue ->
                    onClickSearchWeather(inputValue)
                    showInputDialog = false
                },
                onDismiss = {
                    showInputDialog = false
                }
            )
        }
    }
}


@Composable
fun WeatherContentCard(contentPadding: PaddingValues, weatherData: WeatherData, onClickSearchWeather: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .padding(16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(
                            text = weatherData.temp,
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = stringResource(R.string.current_temperature),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    AsyncImage(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        model = weatherData.iconUrl, // Replace with your image URL
                        contentDescription = weatherData.temp,
                        placeholder = painterResource(R.drawable.baseline_image_24),
                        error = painterResource(R.drawable.baseline_broken_image_24)
                    )
                }
                VerticalSpacer(8.dp)
                ExtraInformationCards(
                    mapOf(
                        stringResource(R.string.feels_like) to weatherData.feelsLike,
                        stringResource(R.string.min) to weatherData.minTemp,
                        stringResource(R.string.max) to weatherData.maxTemp
                    )
                )
                VerticalSpacer(8.dp)
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onClickSearchWeather,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Text(
                        text = stringResource(R.string.search_a_new_location),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun ExtraInformationCards(weatherExtraInfo: Map<String, String>) {
    Row {
        weatherExtraInfo.forEach { weatherInfo ->
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = weatherInfo.value,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = weatherInfo.key,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenContentPreview() {
    AndroidEngineerTestTheme {
        HomeScreenContent(
            homeUiState = HomeUiState.Success(
                WeatherData(temp = "29", minTemp = "21", maxTemp = "35", feelsLike = "32", iconUrl = "")
            ),
            onClickSearchWeather = {}
        )
    }
}
