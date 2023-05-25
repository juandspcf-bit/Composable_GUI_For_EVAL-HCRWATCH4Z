package com.icxcu.adsmartbandapp.screens

sealed class Routes(val route: String) {

    object Permissions: Routes("permissions")
    object BluetoothScanner : Routes("home")
    object DataHome : Routes("dataHome")
    object DataHomeDataPreferences : Routes("dataHomeDataPreferences")
    object LoadingScreen : Routes("loadingScreen")
    object StepsPlots : Routes("stepsPlot")
    object BloodPressurePlots : Routes("bloodPressurePlot")

    object HeartRatePlot : Routes("heartRatePlot")

    object PersonalInfoForm : Routes("personalInfoForm")
    object PersonalInfoFormInit : Routes("personalInfoFormInit")


}