package com.icxcu.adsmartbandapp.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import com.icxcu.adsmartbandapp.R

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "dashboard",
            image = R.drawable.directions_run_24px,
            route = "fields"
        ),
        BarItem(
            title = "vital signals",
            image = R.drawable.vital_signs_24px,
            route = "CheckHealth"
        ),
        BarItem(
            title = "settings",
            image = R.drawable.settings_24px,
            route = "settings"
        )
    )
}