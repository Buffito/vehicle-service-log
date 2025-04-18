package com.thodoris.kotoufos.vehicle_service_log.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4F8DFD),       // Button Blue
    onPrimary = Color.White,          // Text on button
    secondary = Color(0xFFB2C9FF),    // Soft blue tint
    onSecondary = Color(0xFF1C1C1E),
    background = Color(0xFFF5F7FA),   // Overall background
    onBackground = Color(0xFF1C1C1E), // Main text
    surface = Color.White,            // Cards, surfaces
    onSurface = Color(0xFF6B6B6E),    // Subtext
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4F8DFD),
    onPrimary = Color.Black,
    secondary = Color(0xFF5E76D1),
    onSecondary = Color.White,
    background = Color(0xFF121212),
    onBackground = Color(0xFFEEEEEE),
    surface = Color(0xFF1C1C1E),
    onSurface = Color.White,
)

@Composable
fun VehicleservicelogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}