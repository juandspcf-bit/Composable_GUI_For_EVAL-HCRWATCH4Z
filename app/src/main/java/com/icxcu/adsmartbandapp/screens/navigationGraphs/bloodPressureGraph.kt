package com.icxcu.adsmartbandapp.screens.navigationGraphs

import android.app.Application
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.icxcu.adsmartbandapp.screens.BloodPressureNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure.BloodPressureScreenNavStatus
import com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure.BloodPressureScreenRoot
import com.icxcu.adsmartbandapp.screens.viewModelProviders.scopedViewModel
import com.icxcu.adsmartbandapp.viewModels.BloodPressureViewModel
import com.icxcu.adsmartbandapp.viewModels.BloodPressureViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.SharedViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun NavGraphBuilder.bloodPressureGraph(
    sharedViewModel: SharedViewModel,
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

            val bluetoothAddress = if(sharedViewModel.selectedBluetoothDeviceAddress!=""){
                sharedViewModel.selectedBluetoothDeviceAddress
            }else{
                sharedViewModel.lastSelectedBluetoothDeviceAddress[3]
            }

            val bloodPressureViewModel =
                it.scopedViewModel<BloodPressureViewModel, BloodPressureViewModelFactory>(
                    navMainController,
                    "BloodPressureViewModel",
                    BloodPressureViewModelFactory(
                        LocalContext.current.applicationContext
                                as Application
                    )
                )

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