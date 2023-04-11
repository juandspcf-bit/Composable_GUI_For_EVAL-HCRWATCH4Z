package com.icxcu.adsmartbandapp.screens

sealed class Routes(val route: String) {

    object Permissions: Routes("permissions")
    object BluetoothScanner : Routes("home")
    object DataHome : Routes("customers")
    object StepsPlots : Routes("stepsPlot")
    object BloodPressurePlots : Routes("bloodPressurePlot")

}