package com.icxcu.adsmartbandapp.screens.navigationGraphs

import android.app.Application
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.icxcu.adsmartbandapp.screens.PersonalInfoNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.PersonalInfoDataScreenNavStatus
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.PersonalInfoDataScreenRoot
import com.icxcu.adsmartbandapp.screens.viewModelProviders.scopedViewModel
import com.icxcu.adsmartbandapp.viewModels.PersonalInfoViewModel
import com.icxcu.adsmartbandapp.viewModels.PersonalInformationViewModelFactory

fun NavGraphBuilder.personalInfoGraph(
    navMainController: NavHostController,
    startForResult: ActivityResultLauncher<Intent>,
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

            val personalInfoViewModel =
                it.scopedViewModel<PersonalInfoViewModel, PersonalInformationViewModelFactory>(
                    navMainController,
                    "PersonalInfoViewModel",
                    PersonalInformationViewModelFactory(
                        LocalContext.current.applicationContext
                                as Application
                    )
                )

            when(personalInfoViewModel.personalInfoDataScreenNavStatus){
                PersonalInfoDataScreenNavStatus.Leaving->{
                    personalInfoViewModel.personalInfoDataScreenNavStatus = PersonalInfoDataScreenNavStatus.Started
                    personalInfoViewModel.starListeningPersonalInfoDB()
                }
                else->{

                }
            }

            PersonalInfoDataScreenRoot(
                startForResult,
                personalInfoViewModel,
                navMainController
            )
        }
    }
}