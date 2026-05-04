package com.mjtech.core.settings

import com.mjtech.core.common.Result
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSetting(setting: Setting): Flow<Result<Setting>>

    fun saveSetting(setting: Setting): Flow<Result<Unit>>
}