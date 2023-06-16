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


sealed class PhysicalActivityNestedRoute(val route:String, val subRoute:String){
    object PhysicalActivityRoute: PhysicalActivityNestedRoute("PHYSICAL_ACTIVITY", "")
    object PhysicalActivityScreen: PhysicalActivityNestedRoute("PHYSICAL_ACTIVITY", "physical_activity")

}

sealed class PersonalInfoNestedRoute(val route:String, val subRoute:String){
    object PersonalInfoRoute: PersonalInfoNestedRoute("PERSONAL_INFO", "")
    object PersonalInfoScreen: PersonalInfoNestedRoute("PERSONAL_INFO", "personal_info")

}