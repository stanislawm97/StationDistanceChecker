package org.mothdigital.stationdistancechecker.design.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val LightColorScheme = lightColorScheme(
    primary = Plum80,
    onPrimary = Color.White,
    secondary = MustardYellow80,
    onSecondary = Color.Black,
    background = VeryLightGray80,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFFB00020), // Using a common error color for simplicity
    onError = Color.White
)

val DarkColorScheme = darkColorScheme(
    primary = Plum40,
    onPrimary = Color.White,
    secondary = MustardYellow40,
    onSecondary = Color.Black,
    background = VeryLightGray40,
    onBackground = Color.White,
    surface = Color(0xFF121212), // A common surface color for dark themes
    onSurface = Color.White,
    error = Color(0xFFCF6679), // A common error color for dark themes, adjusted for contrast
    onError = Color.Black
)

@Composable
fun StationDistanceCheckerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}