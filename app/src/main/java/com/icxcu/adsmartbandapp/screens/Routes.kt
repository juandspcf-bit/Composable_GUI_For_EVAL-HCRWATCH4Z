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

sealed class PhysicalActivityNestedRoute(){
    class PhysicalActivityMainRoute(var route: String = "PHYSICAL_ACTIVITY") :
        PhysicalActivityNestedRoute()
    class PhysicalActivityScreen(var route: String = "physical_activity"):
        PhysicalActivityNestedRoute()

}

sealed class PersonalInfoNestedRoute{
    class PersonalInfoMainRoute(var route: String = "PERSONAL_INFO") :
        PersonalInfoNestedRoute()
    class PersonalInfoScreen(var route: String = "personal_info") :
        PersonalInfoNestedRoute()

}