package com.icxcu.adsmartbandapp.screens

sealed class Routes(val route: String) {
    object BluetoothScanner : Routes("home")
    object DataHome : Routes("customers")
}