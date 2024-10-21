package com.example.android_engineer_test.ui.home

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.android_engineer_test.R
import com.example.android_engineer_test.model.WeatherDataUi
import com.example.android_engineer_test.ui.theme.AndroidEngineerTestTheme
import com.example.android_engineer_test.ui.widgets.BottomSheetContent
import com.example.android_engineer_test.ui.widgets.MyButton
import com.example.android_engineer_test.ui.widgets.MyProgressBar
import com.example.android_engineer_test.ui.widgets.VerticalSpacer
import java.util.Locale

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = viewModel {
        HomeScreenViewModel(HomeScreenRepo())
    }
) {
    val homeUiState by homeScreenViewModel.homeUiState.collectAsStateWithLifecycle()
    val recentSearchState by homeScreenViewModel.recentSearchesState.collectAsStateWithLifecycle()

    HomeScreenContent(
        homeUiState = homeUiState,
        recentSearchState = recentSearchState,
        onClickSearchWeather = { cityName -> homeScreenViewModel.searchWeather(cityName) },
        onClearRecentSearch = { homeScreenViewModel.clearRecentSearches() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    homeUiState: HomeUiState,
    recentSearchState: List<String>,
    onClickSearchWeather: (cityName: String) -> Unit,
    onClearRecentSearch: () -> Unit,
) {
    val context = LocalContext.current
    var showInputDialog by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(containerColor = MaterialTheme.colorScheme.surfaceContainer) { contentPadding ->

        when (homeUiState) {
            HomeUiState.Init -> {}

            HomeUiState.Loading -> {
                MyProgressBar()
            }

            is HomeUiState.Success -> {
                WeatherContentCard(
                    contentPadding = contentPadding,
                    recentSearchState = recentSearchState,
                    weatherDataUi = homeUiState.weatherDataUi,
                    onClickSearchWeather = { showInputDialog = true },
                    onClearRecentSearch = onClearRecentSearch,
                    onClickCityName = { cityName-> onClickSearchWeather(cityName)  }
                )
            }

            is HomeUiState.Error -> {
                WeatherContentCard(
                    contentPadding = contentPadding,
                    recentSearchState = recentSearchState,
                    weatherDataUi = homeUiState.previousData,
                    onClickSearchWeather = { showInputDialog = true },
                    onClearRecentSearch = onClearRecentSearch,
                    onClickCityName = {  }
                )

                Toast.makeText(context, homeUiState.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        if (showInputDialog) {
            ModalBottomSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                sheetState = bottomSheetState,
                onDismissRequest = { showInputDialog = false },
                windowInsets = WindowInsets(0, 0, 0, 0),
                dragHandle = {},
                containerColor = Color.Transparent,
            ) {
                BottomSheetContent(
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
}


@Composable
fun WeatherContentCard(
    contentPadding: PaddingValues,
    weatherDataUi: WeatherDataUi,
    recentSearchState: List<String>,
    onClickSearchWeather: () -> Unit,
    onClearRecentSearch: () -> Unit,
    onClickCityName: (cityName: String) -> Unit,
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
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Row {
                            Text(
                                text = weatherDataUi.temp,
                                style = MaterialTheme.typography.displayMedium,
                                fontWeight = FontWeight.W900,
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
                MyButton(
                    title = stringResource(R.string.search_a_new_location),
                    onClick = onClickSearchWeather,
                )
            }
        }
        VerticalSpacer(24.dp)

        if (recentSearchState.isNotEmpty()) {
            RecentSearches(
                recentSearchState,
                onClearRecentSearch = onClearRecentSearch,
                onClickCityName = {cityName ->
                    onClickCityName(cityName)
                }
            )
        }
    }
}

@Composable
fun RecentSearches(list: List<String>, onClearRecentSearch: () -> Unit, onClickCityName: (cityName: String) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.recent_searches),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            VerticalSpacer(8.dp)
            LazyColumn(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center) {
                items(list) { cityName ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = { onClickCityName(cityName) })
                            .padding(4.dp),
                    ) {
                        Text(
                            text = cityName,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    VerticalSpacer(8.dp)
                }
            }
            VerticalSpacer(6.dp)
            MyButton(
                title = stringResource(R.string.clear_recent_searches),
                onClick = onClearRecentSearch,
            )
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
                            text = weatherInfo.value + stringResource(R.string.celsius),
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Text(
                        text = weatherInfo.key.uppercase(Locale.ROOT),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
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
            onClickSearchWeather = {},
            onClearRecentSearch = {},
            recentSearchState = listOf("Dhaka", "Chittagong")
        )
    }
}
