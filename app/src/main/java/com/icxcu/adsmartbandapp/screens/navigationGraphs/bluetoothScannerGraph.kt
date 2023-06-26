package com.icxcu.adsmartbandapp.screens.navigationGraphs

import android.app.Activity
import android.app.Application
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.icxcu.adsmartbandapp.data.local.dataPrefrerences.PreferenceDataStoreHelper
import com.icxcu.adsmartbandapp.screens.BluetoothScannerNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.bluetoothScanner.BluetoothScannerRoot
import com.icxcu.adsmartbandapp.screens.viewModelProviders.scopedViewModel
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModel
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.SharedViewModel
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel

fun NavGraphBuilder.bluetoothScannerGraph(
    sharedViewModel: SharedViewModel,
    splashViewModel: SplashViewModel,
    activity: Activity,
    preferenceDataStoreHelper: PreferenceDataStoreHelper,
    navMainController: NavHostController,
){
    navigation(
        startDestination =  BluetoothScannerNestedRoute.BluetoothScannerScreen().route,// "physical_activity",
        route = BluetoothScannerNestedRoute.BluetoothScannerMainRoute().route//"PHYSICAL_ACTIVITY"
    ){

        composable(
            BluetoothScannerNestedRoute.BluetoothScannerScreen().route,
            enterTransition = {
                when (initialState.destination.route) {
                    Routes.Permissions.route -> EnterTransition.None
                    Routes.DataHome.route -> EnterTransition.None
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Routes.Permissions.route -> ExitTransition.None
                    Routes.DataHome.route -> ExitTransition.None
                    else -> null
                }
            }) {

            val bluetoothScannerViewModel =
                it.scopedViewModel<BluetoothScannerViewModel, BluetoothScannerViewModelFactory>(
                    navMainController,
                    "BluetoothScannerViewModel",
                    BluetoothScannerViewModelFactory(
                        LocalContext.current.applicationContext
                                as Application
                    )
                )

            BluetoothScannerRoot(
                bluetoothScannerViewModel,
                sharedViewModel,
                activity,
                splashViewModel,
                preferenceDataStoreHelper,
                navMainController,
            )
        }


    }

}