package com.example.dealdone.ui.theme

import androidx.compose.ui.graphics.Color

data class AppDataState(
    val dynamicThemeState: DynamicThemeState = DynamicThemeState(Color.Unspecified),
    val accessKey: String = ""
)
