package com.icxcu.adsmartbandapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DatePickerColorScheme = darkColorScheme(
    primary = Color.White,
    secondary = Color(0xFFCCC2DC),
    tertiary = Color(0xFFEFB8C8),
    surface = Color.Black,
    outline = Color(0xFFDCE775),
    outlineVariant = Color.Yellow,
)



@Composable
fun DialogsTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DatePickerColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}