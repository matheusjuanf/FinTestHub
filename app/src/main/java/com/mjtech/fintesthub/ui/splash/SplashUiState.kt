package com.mjtech.fintesthub.ui.splash

data class SplashUiState(
    val isLoading: Boolean = true,
    val isReadyToNavigate: Boolean = false,
    val error: String? = null
)
