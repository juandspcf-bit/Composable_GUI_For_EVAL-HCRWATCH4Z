package com.icxcu.adsmartbandapp.screens

sealed class Routes(val route: String) {

    object Permissions: Routes("permissions")
    object CircularProgressLoading : Routes("circularProgressLoading")
    object BluetoothScanner : Routes("home")
    object EnableBluetoothPortScreen : Routes("enableBluetoothPortScreen")
    object DataHome : Routes("dataHome")
    object PhysicalActivity : Routes("physicalActivity")
    object BloodPressurePlots : Routes("bloodPressurePlot")

    object HeartRatePlot : Routes("heartRatePlot")

}

sealed class PhysicalActivityNestedRoute(){
    class PhysicalActivityMainRoute(var route: String = "PHYSICAL_ACTIVITY") :
        PhysicalActivityNestedRoute()
    class PhysicalActivityScreen(var route: String = "physical_activity"):
        PhysicalActivityNestedRoute()

}

sealed class BloodPressureNestedRoute{
    class BloodPressureMainRoute(var route: String = "BLOOD_PRESSURE") :
        BloodPressureNestedRoute()
    class BloodPressureScreen(var route: String = "blood_pressure"):
        BloodPressureNestedRoute()

}

sealed class HeartRateNestedRoute{
    class HeartRateMainRoute(var route: String = "HEART_RATE") :
        BloodPressureNestedRoute()
    class HeartRateScreen(var route: String = "heart_rate"):
        BloodPressureNestedRoute()

}

sealed class MainNavigationNestedRoute{
    class MainNavigationMainRoute(var route: String = "MAIN_NAVIGATION_ROUTE") :
        MainNavigationNestedRoute()
    class MainNavigationScreen(var route: String = "main_navigation_route"):
        MainNavigationNestedRoute()

}

sealed class BluetoothScannerNestedRoute{
    class BluetoothScannerMainRoute(var route: String = "BLUETOOTH_SCANNER_ROUTE") :
        BluetoothScannerNestedRoute()
    class BluetoothScannerScreen(var route: String = "bluetooth_scanner_route"):
        BluetoothScannerNestedRoute()

}


sealed class PersonalInfoNestedRoute{
    class PersonalInfoMainRoute(var route: String = "PERSONAL_INFO") :
        PersonalInfoNestedRoute()
    class PersonalInfoScreen(var route: String = "personal_info") :
        PersonalInfoNestedRoute()
}

sealed class PersonalInfoInitNestedRoute{
    class PersonalInfoInitMainRoute(var route: String = "PERSONAL_INIT_INFO") :
        PersonalInfoNestedRoute()
    class PersonalInfoInitScreen(var route: String = "personal_init_info") :
        PersonalInfoNestedRoute()

}