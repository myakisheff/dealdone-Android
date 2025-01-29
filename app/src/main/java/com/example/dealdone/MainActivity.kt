package com.example.dealdone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dealdone.ui.DealDoneApp
import com.example.dealdone.ui.theme.DealDoneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DealDoneTheme {
                DealDoneApp()
            }
        }
    }
}
