package com.example.android_engineer_test.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_engineer_test.R
import com.example.android_engineer_test.ui.theme.AndroidEngineerTestTheme
import com.example.android_engineer_test.ui.widgets.InputDialog
import com.example.android_engineer_test.ui.widgets.VerticalSpacer

@Composable
fun HomeScreen() {

    HomeScreenContent(onClickSearchWeather = {})
}

@Composable
fun HomeScreenContent(onClickSearchWeather: () -> Unit) {
    
    var showInputDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(containerColor = MaterialTheme.colorScheme.surfaceContainer) { contentPadding ->
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
                                text = "24",
                                style = MaterialTheme.typography.displayLarge,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = stringResource(R.string.current_temperature),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Image(
                            modifier = Modifier.size(50.dp),
                            imageVector = Icons.Filled.Home,
                            contentDescription = ""
                        )
                    }
                    VerticalSpacer(8.dp)
                    ExtraInformationCards(mapOf())
                    VerticalSpacer(8.dp)
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { showInputDialog = true },
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

        if (showInputDialog) {
            InputDialog(
                title = stringResource(R.string.enter_a_location),
                onClick = { inputValue ->

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
        HomeScreenContent(onClickSearchWeather = {})
    }
}
