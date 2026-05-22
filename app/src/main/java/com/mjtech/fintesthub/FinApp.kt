package com.mjtech.fintesthub

import android.app.Application
import com.mjtech.core.settings.Setting
import com.mjtech.fintesthub.data.settings.MainSettingsKeys.DARK_MODE
import com.mjtech.fintesthub.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FinApp : Application() {

    companion object {
        val DEFAULT_SETTINGS = listOf(
            Setting(key = DARK_MODE, value = false)
        )
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FinApp)
            modules(
                appModule,
                integrationModule
            )
        }
    }
}