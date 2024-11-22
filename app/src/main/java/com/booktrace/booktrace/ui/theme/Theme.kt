package com.booktrace.booktrace.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Purple80,
    onPrimary = Color.White,
    secondary = Purple80,
    background = White80,
    onBackground = Color.Black
)

private val DarkColors = darkColorScheme(
    primary = Purple80,
    onPrimary = Color.Black,
    secondary = Teal80,
    background = Color.Black,
    onBackground = Color.White
)

val MetallicPurpleGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF6B2FCC),
        Color(0xFF994CF1),
        Color(0xFFAB79FF),
    )
)

// Create a composable to apply the gradient
@Composable
fun GradientBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(modifier = modifier.background(MetallicPurpleGradient).fillMaxSize()){
        content()
    }
}

@Composable
fun BooktraceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}