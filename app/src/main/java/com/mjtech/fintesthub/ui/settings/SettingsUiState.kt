package com.mjtech.fintesthub.ui.settings

import com.mjtech.core.common.Result

data class SettingsUiState(
    val editableSettings: EditableSettings = emptyMap(),
    val saveSettingsResult: Result<Unit>? = null,
    val isLoading: Boolean = true,
    val showSuccessMessage: Boolean = false
)