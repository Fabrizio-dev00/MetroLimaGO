package com.miempresa.metrolimago.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = ButtonColor,
    secondary = PurpleGradientEnd,
    background = BackgroundLight,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = TextPrimaryLight,
)

private val DarkColors = darkColorScheme(
    primary = ButtonColor,
    secondary = PurpleGradientEnd,
    background = BackgroundDark,
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onBackground = TextPrimaryDark,
)

@Composable
fun MetroLimaGOTheme(
    // ✅ Ahora tiene valor por defecto, así no da error en Previews ni en pantallas sin ViewModel
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}

// 🎨 Gradiente reutilizable
val MetroGradient = Brush.verticalGradient(
    colors = listOf(PurpleGradientStart, PurpleGradientEnd)
)
