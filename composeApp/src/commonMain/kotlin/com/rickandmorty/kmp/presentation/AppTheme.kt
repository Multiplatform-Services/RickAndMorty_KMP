package com.rickandmorty.kmp.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LightColors = lightColorScheme(
    primary = Color(0xFF00FF00),            // Bright Green
    primaryContainer = Color(0xFF66FF66),    // Light Green
    secondary = Color(0xFF0000FF),           // Bright Blue
    secondaryContainer = Color(0xFF6666FF),  // Light Blue
    tertiary = Color(0xFF9933FF),            // Bright Purple
    surface = Color.White,
    background = Color(0xFFE0E0E0),          // Light Gray
    error = Color(0xFFFF0000),               // Bright Red
    onBackground = Color.Black,
    onError = Color.White,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onSurface = Color.Black,
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF004400),             // Dark Green
    primaryContainer = Color(0xFF002200),    // Darker Green
    secondary = Color(0xFF000088),           // Dark Blue
    secondaryContainer = Color(0xFF000044),  // Darker Blue
    tertiary = Color(0xFF5500AA),            // Dark Purple
    surface = Color(0xFF303030),             // Dark Gray
    background = Color.Black,
    error = Color(0xFFFF0000),               // Bright Red
    onBackground = Color.White,
    onError = Color.Black,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = Color.White,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    val petMeFontFamily = FontFamily(Font(10, FontWeight.Medium, FontStyle.Normal))

    val typography = Typography(
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 8.sp
        ),
        titleMedium = TextStyle(
            fontFamily = if (LocalInspectionMode.current)
                FontFamily.Monospace
            else
                petMeFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 2.sp
        ),
    )

    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}