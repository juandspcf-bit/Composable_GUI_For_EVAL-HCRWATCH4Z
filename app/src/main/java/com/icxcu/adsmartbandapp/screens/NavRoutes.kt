package com.icxcu.adsmartbandapp.screens

sealed class NavRoutes(val route: String) {
    object Fields : NavRoutes("fields")
    object CheckHealth : NavRoutes("CheckHealth")
    object Settings : NavRoutes("settings")
}