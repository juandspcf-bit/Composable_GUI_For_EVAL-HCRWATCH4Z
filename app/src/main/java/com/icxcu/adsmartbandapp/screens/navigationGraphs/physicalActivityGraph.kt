package com.icxcu.adsmartbandapp.screens.navigationGraphs

import android.app.Application
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.icxcu.adsmartbandapp.screens.PhysicalActivityNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.PhysicalActivityScreenNavStatus
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.PhysicalActivityScreenRoot
import com.icxcu.adsmartbandapp.screens.viewModelProviders.scopedViewModel
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModel
import com.icxcu.adsmartbandapp.viewModels.PhysicalActivityViewModel
import com.icxcu.adsmartbandapp.viewModels.PhysicalActivityViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun NavGraphBuilder.physicalActivityGraph(
    bluetoothScannerViewModel: BluetoothScannerViewModel,
    splashViewModel: SplashViewModel,
    navMainController: NavHostController
){
    navigation(
        startDestination = PhysicalActivityNestedRoute.PhysicalActivityScreen().route,// "physical_activity",
        route = PhysicalActivityNestedRoute.PhysicalActivityMainRoute().route//"PHYSICAL_ACTIVITY"
    ){
        composable(
            PhysicalActivityNestedRoute.PhysicalActivityScreen().route,
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

            val physicalActivityViewModel =
                it.scopedViewModel<PhysicalActivityViewModel, PhysicalActivityViewModelFactory>(
                    navMainController,
                    "PhysicalActivityViewModel",
                    PhysicalActivityViewModelFactory(
                        LocalContext.current.applicationContext
                                as Application
                    )
                )


            val myDateObj = LocalDateTime.now()
            val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val todayFormattedDate = myDateObj.format(myFormatObj)

            when(physicalActivityViewModel.physicalActivityScreenNavStatus){
                PhysicalActivityScreenNavStatus.Leaving->{
                    physicalActivityViewModel.physicalActivityScreenNavStatus = PhysicalActivityScreenNavStatus.Started
                    physicalActivityViewModel.starListeningDayPhysicalActivityDB(todayFormattedDate, bluetoothAddress, )
                }
                else->{

                }
            }
            PhysicalActivityScreenRoot(
                physicalActivityViewModel = physicalActivityViewModel,
                bluetoothAddress,
                navMainController
            )
        }
    }


}