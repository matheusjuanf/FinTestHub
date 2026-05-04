package com.mjtech.fintesthub.ui.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mjtech.fintesthub.data.settings.MainSettingsKeys
import com.mjtech.fintesthub.ui.common.components.FinSwitch
import org.koin.androidx.compose.koinViewModel


@Composable
fun SettingsPage(
    viewModel: SettingsViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
            return
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    val isPrintingEnabled =
                        uiState.editableSettings[MainSettingsKeys.PRINT_RECEIPT] as? Boolean
                            ?: false

                    item {
                        FinSwitch(
                            title = "Imprimir comprovantes",
                            subtitle = "Habilitar impressão automática de comprovantes",
                            isChecked = isPrintingEnabled,
                            onCheckedChange = viewModel::onPrintReceiptToggle
                        )
                    }
                }
            }
        }
    }
}