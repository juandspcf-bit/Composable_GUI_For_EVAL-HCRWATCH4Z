package com.icxcu.adsmartbandapp.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Home",
            image = Icons.Filled.Home,
            route = "fields"
        ),
        BarItem(
            title = "Check your Health",
            image = Icons.Filled.Face,
            route = "CheckHealth"
        ),
        BarItem(
            title = "Settings",
            image = Icons.Filled.Favorite,
            route = "settings"
        )
    )
}