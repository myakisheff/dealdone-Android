package com.example.dealdone.ui.theme


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ThemeViewModel : ViewModel() {
    private val _themeState = MutableStateFlow(ThemeState())
    val themeState: StateFlow<ThemeState> = _themeState

    fun changeThemeColor(color: Color) {
        _themeState.update { state ->
            state.copy(
                dynamicThemeState = DynamicThemeState(initialPrimaryColor = color)
            )
        }
    }

    fun changeThemeColorIfUnspecified(color: Color) {
        if(_themeState.value.dynamicThemeState.primaryColor == Color.Unspecified) {
            changeThemeColor(color)
        }
    }
}