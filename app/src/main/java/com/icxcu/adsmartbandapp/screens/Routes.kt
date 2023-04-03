package com.icxcu.adsmartbandapp.screens

sealed class Routes(val route: String) {
    val NAVBAR_HOME = "navBarHome"

    object Permissions: Routes("permissions")
    object BluetoothScanner : Routes("home")
    object DataHome : Routes("customers")
    object StepsPlots : Routes("stepsPlot")

}