package com.mjtech.fintesthub.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MainColor,
    onPrimary = White100,
    secondary = MainLightColor,
    tertiary = SecondaryColor,
    background = BackgroundDarkColor,
    surface = Gray600,
    onBackground = White100,
    onSurface = White100,
    onSurfaceVariant = Gray300
)

private val LightColorScheme = lightColorScheme(
    primary = MainColor,
    secondary = MainLightColor,
    tertiary = SecondaryColor,
    background = BackgroundLightColor,
    surface = Color.White,
    onBackground = Gray600,
    onSurface = Gray600,
    onSurfaceVariant = Gray400,
)

val LocalDarkTheme = compositionLocalOf { false }

@Composable
fun FinAndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(LocalDarkTheme provides darkTheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}