package com.example.android_engineer_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.android_engineer_test.ui.home.HomeScreen
import com.example.android_engineer_test.ui.theme.AndroidEngineerTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidEngineerTestTheme {
                HomeScreen()
            }
        }
    }
}