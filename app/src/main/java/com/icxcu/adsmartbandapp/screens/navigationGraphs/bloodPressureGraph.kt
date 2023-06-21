package com.icxcu.adsmartbandapp.screens.navigationGraphs

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.icxcu.adsmartbandapp.screens.BloodPressureNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure.BloodPressureScreenNavStatus
import com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure.BloodPressureScreenRoot
import com.icxcu.adsmartbandapp.screens.viewModelProviders.bloodPressureViewModel
import com.icxcu.adsmartbandapp.viewModels.BloodPressureViewModel
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModel
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun NavGraphBuilder.bloodPressureGraph(
    bluetoothScannerViewModel: BluetoothScannerViewModel,
    splashViewModel: SplashViewModel,
    navMainController: NavHostController
){
    navigation(
        startDestination = BloodPressureNestedRoute.BloodPressureScreen().route,// "physical_activity",
        route = BloodPressureNestedRoute.BloodPressureMainRoute().route//"PHYSICAL_ACTIVITY"
    ){
        composable(
            BloodPressureNestedRoute.BloodPressureScreen().route,
            enterTransition = {
                when (initialState.destination.route) {
                    Routes.DataHome.route ->
                        EnterTransition.None

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Routes.DataHome.route -> ExitTransition.None
                    else -> null
                }
            }
        ) {

            val bluetoothAddress = if(bluetoothScannerViewModel.selectedBluetoothDeviceAddress!=""){
                bluetoothScannerViewModel.selectedBluetoothDeviceAddress
            }else{
                splashViewModel.lastAccessedDevice[3]
            }

            val bloodPressureViewModel = it.bloodPressureViewModel<BloodPressureViewModel>(navController = navMainController)

            val myDateObj = LocalDateTime.now()
            val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val todayFormattedDate = myDateObj.format(myFormatObj)

            when(bloodPressureViewModel.bloodPressureScreenNavStatus){
                BloodPressureScreenNavStatus.Leaving->{
                    bloodPressureViewModel.bloodPressureScreenNavStatus = BloodPressureScreenNavStatus.Started
                    bloodPressureViewModel.starListeningBloodPressureDB(todayFormattedDate, bluetoothAddress)
                }
                else->{

                }
            }

            BloodPressureScreenRoot(
                bloodPressureViewModel,
                bluetoothAddress,
                navMainController
            )
        }
    }
}