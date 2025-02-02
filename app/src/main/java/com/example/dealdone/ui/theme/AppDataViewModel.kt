package com.example.dealdone.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dealdone.data.repository.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private object PreferencesKeys {
    val ACCESS_KEY = stringPreferencesKey("access_key")
    val THEME_COLOR = intPreferencesKey("theme_color")
}

class AppDataViewModel(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _appDataState = MutableStateFlow(AppDataState())
    val appDataState: StateFlow<AppDataState> = _appDataState

    init {
        viewModelScope.launch {
            launch {
                dataStoreManager.read(
                    key = PreferencesKeys.ACCESS_KEY,
                    defaultValue = ""
                ).collect { key ->
                    _appDataState.update { state ->
                        state.copy(
                            accessKey = key
                        )
                    }
                }
            }
            launch {
                dataStoreManager.read(
                    key = PreferencesKeys.THEME_COLOR,
                    defaultValue = 0
                ).collect { color ->
                    _appDataState.update { state ->
                        state.copy(
                            dynamicThemeState = DynamicThemeState(Color(color))
                        )
                    }
                }
            }
        }


    }

    fun changeAccessKey(key: String) {
        _appDataState.update { state ->
            state.copy(
                accessKey = key
            )
        }
    }

    fun changeThemeColor(color: Color) {
        _appDataState.update { state ->
            state.copy(
                dynamicThemeState = DynamicThemeState(initialPrimaryColor = color)
            )
        }
    }

    fun changeThemeColorIfUnspecified(color: Color) {
        if(_appDataState.value.dynamicThemeState.primaryColor == Color.Unspecified) {
            changeThemeColor(color)
        }
    }

    suspend fun saveThemeColor() {
        dataStoreManager.save(
            key = PreferencesKeys.THEME_COLOR,
            value = _appDataState.value.dynamicThemeState.primaryColorArgb
        )
    }

    suspend fun saveAccessKey() {
        dataStoreManager.save(
            key = PreferencesKeys.ACCESS_KEY,
            value = _appDataState.value.accessKey
        )
    }
}

class AppDataViewModelFactory(private val dataStoreManager: DataStoreManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AppDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppDataViewModel(dataStoreManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}