package com.example.android_engineer_test.ui.home

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.android_engineer_test.R
import com.example.android_engineer_test.model.WeatherDataUi
import com.example.android_engineer_test.ui.theme.AndroidEngineerTestTheme
import com.example.android_engineer_test.ui.widgets.InputDialog
import com.example.android_engineer_test.ui.widgets.MyProgressBar
import com.example.android_engineer_test.ui.widgets.VerticalSpacer

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = viewModel {
        HomeScreenViewModel(HomeScreenRepo())
    }
) {
    val homeUiState by homeScreenViewModel.homeUiState.collectAsState()

    HomeScreenContent(
        homeUiState = homeUiState,
        onClickSearchWeather = { cityName -> homeScreenViewModel.searchWeather(cityName) }
    )
}

@Composable
fun HomeScreenContent(homeUiState: HomeUiState, onClickSearchWeather: (cityName: String) -> Unit) {
    val context = LocalContext.current
    var showInputDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(containerColor = MaterialTheme.colorScheme.surfaceContainer) { contentPadding ->

        when (homeUiState) {
            HomeUiState.Init -> {}

            HomeUiState.Loading -> {
                MyProgressBar()
            }

            is HomeUiState.Success -> {
                WeatherContentCard(
                    contentPadding = contentPadding,
                    weatherDataUi = homeUiState.weatherDataUi,
                    onClickSearchWeather = { showInputDialog = true }
                )
            }

            is HomeUiState.Error -> {
                WeatherContentCard(
                    contentPadding = contentPadding,
                    weatherDataUi = homeUiState.previousData,
                    onClickSearchWeather = { showInputDialog = true }
                )

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
fun WeatherContentCard(
    contentPadding: PaddingValues,
    weatherDataUi: WeatherDataUi,
    onClickSearchWeather: () -> Unit
) {
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
                        Row {
                            Text(
                                text = weatherDataUi.temp,
                                style = MaterialTheme.typography.displayLarge,
                                fontWeight = FontWeight.ExtraBold,
                            )
                            Text(
                                text = stringResource(R.string.celsius),
                                color = MaterialTheme.colorScheme.onSurface,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W900,
                                    baselineShift = BaselineShift(-.3f) // Adjust the value as needed
                                )
                            )
                        }
                        Text(
                            text = stringResource(R.string.current_temperature),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    AsyncImage(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        model = weatherDataUi.iconUrl, // Replace with your image URL
                        contentDescription = weatherDataUi.temp,
                        placeholder = painterResource(R.drawable.baseline_image_24),
                        error = painterResource(R.drawable.baseline_broken_image_24)
                    )
                }
                VerticalSpacer(8.dp)
                ExtraInformationCards(
                    mapOf(
                        stringResource(R.string.feels_like) to weatherDataUi.feelsLike,
                        stringResource(R.string.min) to weatherDataUi.minTemp,
                        stringResource(R.string.max) to weatherDataUi.maxTemp
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
                        .height(100.dp)
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box( modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            text = weatherInfo.value,
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                        )
                    }
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
                WeatherDataUi(
                    temp = "29",
                    minTemp = "21",
                    maxTemp = "35",
                    feelsLike = "32",
                    iconUrl = ""
                )
            ),
            onClickSearchWeather = {}
        )
    }
}
