package com.icxcu.adsmartbandapp.screens

sealed class Routes(val route: String) {

    object Permissions: Routes("permissions")
    object CircularProgressLoading : Routes("circularProgressLoading")
    object BluetoothScanner : Routes("home")
    object DataHomeFromBluetoothScannerScreen : Routes("dataHomeFromBluetoothScannerScreen")
    object DataHome : Routes("dataHome")
    object PhysicalActivity : Routes("physicalActivity")
    object BloodPressurePlots : Routes("bloodPressurePlot")

    object HeartRatePlot : Routes("heartRatePlot")

    object PersonalInfoForm : Routes("personalInfoForm")
    object PersonalInfoFormInit : Routes("personalInfoFormInit")


}