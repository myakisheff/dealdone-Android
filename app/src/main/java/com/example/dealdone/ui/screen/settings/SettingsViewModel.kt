package com.example.dealdone.ui.screen.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel: ViewModel() {
    private val _settingsUiState = MutableStateFlow(SettingsUiState())
    val settingsUiState: StateFlow<SettingsUiState> = _settingsUiState

    fun changeKeyVisibility() {
        _settingsUiState.update { state ->
            state.copy(
                isKeyVisible = !state.isKeyVisible
            )
        }
    }

    fun hideColorPicker() {
        _settingsUiState.update { state ->
            state.copy(
                isColorPickerVisible = false
            )
        }
    }

    fun showColorPicker() {
        _settingsUiState.update { state ->
            state.copy(
                isColorPickerVisible = true
            )
        }
    }
}