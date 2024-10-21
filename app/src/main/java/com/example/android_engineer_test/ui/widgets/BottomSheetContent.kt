package com.example.android_engineer_test.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_engineer_test.R
import com.example.android_engineer_test.ui.theme.AndroidEngineerTestTheme


@Composable
fun BottomSheetContent(title: String, onClick: (inputValue: String) -> Unit, onDismiss: () -> Unit) {
    var inputValue by rememberSaveable { mutableStateOf("Dhaka") }

    Box(modifier = Modifier.fillMaxSize().navigationBarsPadding().padding(bottom = 24.dp)) {
        Card(
            modifier = Modifier.align(Alignment.BottomCenter),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(18.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                VerticalSpacer(24.dp)
                TextField(
                    value = inputValue,
                    onValueChange = { inputValue = it },
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.outlineVariant
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    ),
                )
                VerticalSpacer(24.dp)
                Button(
                    modifier = Modifier.fillMaxWidth(),
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
        BottomSheetContent(
            title = stringResource(R.string.enter_a_location),
            onClick = {},
            onDismiss = {}
        )
    }
}