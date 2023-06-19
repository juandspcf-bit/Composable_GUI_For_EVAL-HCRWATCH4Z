package com.icxcu.adsmartbandapp.screens.navigationGraphs

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.icxcu.adsmartbandapp.screens.PersonalInfoNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.PersonalInfoDataScreenNavStatus
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.PersonalInfoDataScreenRoot
import com.icxcu.adsmartbandapp.screens.viewModelProviders.personalInfoViewModel
import com.icxcu.adsmartbandapp.viewModels.PersonalInfoViewModel
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel

fun NavGraphBuilder.personalInfoGraph(
    splashViewModel: SplashViewModel,
    navMainController: NavHostController
){
    navigation(
        startDestination = PersonalInfoNestedRoute.PersonalInfoScreen().route,
        route = PersonalInfoNestedRoute.PersonalInfoMainRoute().route
    ){

        composable(
            PersonalInfoNestedRoute.PersonalInfoScreen().route,
            enterTransition = {
                when (initialState.destination.route) {
                    Routes.DataHome.route -> EnterTransition.None
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

            val personalInfoViewModel = it.personalInfoViewModel<PersonalInfoViewModel>(navController = navMainController)

            when(personalInfoViewModel.personalInfoDataScreenNavStatus){
                PersonalInfoDataScreenNavStatus.Leaving->{
                    personalInfoViewModel.personalInfoDataScreenNavStatus = PersonalInfoDataScreenNavStatus.Started
                    personalInfoViewModel.starListeningPersonalInfoDB(bluetoothAddress)
                }
                else->{

                }
            }

            PersonalInfoDataScreenRoot(
                personalInfoViewModel,
                bluetoothAddress,
                navMainController)
        }
    }
}