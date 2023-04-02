package com.icxcu.adsmartbandapp.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import com.icxcu.adsmartbandapp.R

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Home",
            image = R.drawable.baseline_directions_run_24,
            route = "fields"
        ),
        BarItem(
            title = "Check your Health",
            image = R.drawable.baseline_vibration_24,
            route = "CheckHealth"
        ),
        BarItem(
            title = "Settings",
            image = R.drawable.baseline_settings_24,
            route = "settings"
        )
    )
}