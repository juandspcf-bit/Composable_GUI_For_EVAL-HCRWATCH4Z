package com.icxcu.adsmartbandapp.screens.navigationGraphs

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.icxcu.adsmartbandapp.screens.HeartRateNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.plotsFields.heartRate.HeartRateScreenNavStatus
import com.icxcu.adsmartbandapp.screens.plotsFields.heartRate.HeartRateScreenRoot
import com.icxcu.adsmartbandapp.screens.viewModelProviders.heartRateViewModel
import com.icxcu.adsmartbandapp.viewModels.HeartRateViewModel
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun NavGraphBuilder.heartRateGraph(
    splashViewModel: SplashViewModel,
    navMainController: NavHostController
){
    navigation(
        startDestination = HeartRateNestedRoute.HeartRateScreen().route,
        route = HeartRateNestedRoute.HeartRateMainRoute().route,
    ){
        composable(
            HeartRateNestedRoute.HeartRateScreen().route,
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

            val bluetoothAddress = splashViewModel.lastAccessedDevice[3]

            val heartRateViewModel = it.heartRateViewModel<HeartRateViewModel>(navController = navMainController)

            val myDateObj = LocalDateTime.now()
            val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val todayFormattedDate = myDateObj.format(myFormatObj)

            when(heartRateViewModel.heartRateScreenNavStatus){
                HeartRateScreenNavStatus.Leaving->{
                    heartRateViewModel.heartRateScreenNavStatus = HeartRateScreenNavStatus.Started
                    heartRateViewModel.starListeningHeartRateDB(todayFormattedDate, bluetoothAddress)
                    heartRateViewModel.starListeningPersonalInfoDB()
                }
                else->{

                }
            }

            HeartRateScreenRoot(
                heartRateViewModel,
                bluetoothAddress,
                navMainController
            )
        }
    }
}