package com.icxcu.adsmartbandapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat



private val ADSmartBandAppScheme = darkColorScheme(
    primary = Color(0xFF6650a4),
    secondary = Color.White,
    tertiary = Pink40,
    surface = Color(0xff0d1721), // date picker //top app bar
    onSurface = Color.White,
    surfaceVariant = Color.DarkGray, // cards
    //primaryContainer =  Color(0xFFCDDC39),
    //onPrimaryContainer: Color,
    background = Color(0xff1d2a35),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),

    /* Other default colors to override




    */
)

@Composable
fun ADSmartBandAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = ADSmartBandAppScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}