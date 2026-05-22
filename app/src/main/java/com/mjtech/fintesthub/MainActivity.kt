package com.mjtech.fintesthub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mjtech.core.settings.Setting
import com.mjtech.core.settings.SettingsRepository
import com.mjtech.fintesthub.data.settings.MainSettingsKeys
import com.mjtech.fintesthub.ui.common.routes.AppNavigation
import com.mjtech.fintesthub.ui.theme.FinAndroidTheme
import org.koin.android.ext.android.inject
import kotlin.getValue
import com.mjtech.core.common.Result

class MainActivity : ComponentActivity() {

    private val settingsRepository by inject<SettingsRepository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val settingsResultState by settingsRepository
                .getSetting(Setting(MainSettingsKeys.DARK_MODE, false))
                .collectAsState(initial = Result.Loading)

            val isDarkMode = when (val result = settingsResultState) {
                is Result.Success -> {
                    result.data.value as? Boolean ?: false
                }
                else -> {
                    false
                }
            }

            FinAndroidTheme(darkTheme = isDarkMode) {
                Scaffold (
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppNavigation(paddingValues = innerPadding)
                }
            }
        }
    }
}