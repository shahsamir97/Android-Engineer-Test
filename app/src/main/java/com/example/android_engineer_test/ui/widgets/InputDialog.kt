package com.example.android_engineer_test.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.android_engineer_test.R
import com.example.android_engineer_test.ui.theme.AndroidEngineerTestTheme


@Composable
fun InputDialog(title: String, onClick:(inputValue: String) -> Unit, onDismiss: () -> Unit) {
    var inputValue by rememberSaveable { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                VerticalSpacer(12.dp)
                OutlinedTextField(
                    value = inputValue,
                    onValueChange = { inputValue = it },
                    textStyle = MaterialTheme.typography.headlineSmall
                )
                VerticalSpacer(12.dp)
                Button(
                    onClick = { onClick(inputValue) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface)
                ) {
                    Text(text = stringResource(R.string.search_current_weather))
                }
            }
        }
    }
}

@Preview
@Composable
fun InputDialogPreview() {
    AndroidEngineerTestTheme {
        InputDialog(
            title = stringResource(R.string.enter_a_location),
            onClick = {},
            onDismiss = {}
        )
    }
}