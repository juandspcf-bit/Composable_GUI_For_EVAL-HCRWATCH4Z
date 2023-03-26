package com.icxcu.adsmartbandapp.screens

sealed class Routes(val route: String) {
    val NAVBAR_HOME = "navBarHome"

    object BluetoothScanner : Routes("home")
    object DataHome : Routes("customers")


}