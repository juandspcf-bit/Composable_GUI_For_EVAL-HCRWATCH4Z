package com.icxcu.adsmartbandapp.screens.navigationGraphs

import android.app.Application
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.icxcu.adsmartbandapp.screens.MainNavigationNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.mainNavBar.MainNavigationBarRoot
import com.icxcu.adsmartbandapp.screens.mainNavBar.StatusReadingDbForDashboard
import com.icxcu.adsmartbandapp.screens.viewModelProviders.scopedViewModel
import com.icxcu.adsmartbandapp.viewModels.MainNavigationModelFactory
import com.icxcu.adsmartbandapp.viewModels.MainNavigationViewModel
import com.icxcu.adsmartbandapp.viewModels.SharedViewModel
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel

fun NavGraphBuilder.mainNavigationGraph(
    splashViewModel: SplashViewModel,
    sharedViewModel: SharedViewModel,
    navMainController:NavHostController
) {

    navigation(
        startDestination = MainNavigationNestedRoute.MainNavigationScreen().route,// "physical_activity",
        route = MainNavigationNestedRoute.MainNavigationMainRoute().route//"PHYSICAL_ACTIVITY"
    ){

        composable(
            route = MainNavigationNestedRoute.MainNavigationScreen().route,                    // declaring placeholder in String route
            enterTransition = {
                when (initialState.destination.route) {
                    Routes.PhysicalActivity.route -> EnterTransition.None
                    Routes.BloodPressurePlots.route -> EnterTransition.None
                    Routes.HeartRatePlot.route -> EnterTransition.None
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Routes.PhysicalActivity.route -> ExitTransition.None
                    Routes.BloodPressurePlots.route -> ExitTransition.None
                    Routes.HeartRatePlot.route -> ExitTransition.None
                    else -> null
                }
            }
        ) {

            val mainNavigationViewModel =
                it.scopedViewModel<MainNavigationViewModel, MainNavigationModelFactory>(
                    navMainController,
                    "MainNavViewModel",
                    MainNavigationModelFactory(
                        LocalContext.current.applicationContext
                                as Application
                    )
                )

            val bluetoothName = if(sharedViewModel.selectedBluetoothDeviceName!=""){
                sharedViewModel.selectedBluetoothDeviceName
            }else{
                splashViewModel.lastAccessedDevice[2]
            }
            val bluetoothAddress = if(sharedViewModel.selectedBluetoothDeviceAddress!=""){
                sharedViewModel.selectedBluetoothDeviceAddress
            }else{
                splashViewModel.lastAccessedDevice[3]
            }

            if (mainNavigationViewModel.statusReadingDbForDashboard != StatusReadingDbForDashboard.NoRead
                && mainNavigationViewModel.statusReadingDbForDashboard != StatusReadingDbForDashboard.ReadyForNewReadFromFieldsPlot
            ) {
                mainNavigationViewModel.statusReadingDbForDashboard =
                    StatusReadingDbForDashboard.ReadyForNewReadFromDashBoard
            }

            MainNavigationBarRoot(
                mainNavigationViewModel,
                bluetoothAddress,
                bluetoothName,
                navMainController
            )
        }
    }

}
