package com.mjtech.fintesthub.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjtech.core.common.Result
import com.mjtech.core.settings.Setting
import com.mjtech.core.settings.Settings
import com.mjtech.core.settings.SettingsRepository
import com.mjtech.fintesthub.data.settings.MainSettingsKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

typealias EditableSettings = Map<String, Any>

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadEditableSettings()
    }

    fun onDarkModeToggle(isChecked: Boolean) {
        val key = MainSettingsKeys.DARK_MODE

        updateSettingValue(key, isChecked)

        saveSetting(key, isChecked)
    }

    private fun updateSettingValue(key: String, newValue: Any) {
        _uiState.update { currentState ->
            val newMap = currentState.editableSettings.toMutableMap()
            newMap[key] = newValue

            currentState.copy(editableSettings = newMap)
        }
    }

    private fun saveSetting(key: String, newValue: Any) {
        val settingToSave = Setting(key, newValue)

        viewModelScope.launch {
            settingsRepository.saveSetting(settingToSave).collect { result ->
                if (result is Result.Success) {
                    Settings.updateSetting(settingToSave)
                } else if (result is Result.Error) {
                    Log.e(TAG, result.error)
                }
            }
        }
    }

    private fun loadEditableSettings() {
        _uiState.update { currentState ->
            currentState.copy(
                editableSettings = Settings.settingsMap,
                isLoading = false
            )
        }
    }

    companion object {
        val TAG = SettingsViewModel::class.java.simpleName
    }
}