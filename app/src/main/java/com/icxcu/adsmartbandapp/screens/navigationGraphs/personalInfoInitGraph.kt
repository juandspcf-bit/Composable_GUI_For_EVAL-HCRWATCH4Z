package com.icxcu.adsmartbandapp.screens.navigationGraphs

import android.app.Application
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.icxcu.adsmartbandapp.screens.PersonalInfoInitNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.PersonalInfoDataInitScreenRoot
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.PersonalInfoDataScreenNavStatus
import com.icxcu.adsmartbandapp.screens.viewModelProviders.scopedViewModel
import com.icxcu.adsmartbandapp.viewModels.PersonalInfoViewModel
import com.icxcu.adsmartbandapp.viewModels.PersonalInformationViewModelFactory

fun NavGraphBuilder.personalInfoInitGraph(
    navMainController: NavHostController
) {
    navigation(
        startDestination = PersonalInfoInitNestedRoute.PersonalInfoInitScreen().route,
        route = PersonalInfoInitNestedRoute.PersonalInfoInitMainRoute().route
    ) {

        composable(
            PersonalInfoInitNestedRoute.PersonalInfoInitScreen().route,
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
                )//it.personalInfoViewModel<PersonalInfoViewModel>(navController = navMainController)

            when (personalInfoViewModel.personalInfoDataScreenNavStatus) {
                PersonalInfoDataScreenNavStatus.Leaving -> {
                    personalInfoViewModel.personalInfoDataScreenNavStatus =
                        PersonalInfoDataScreenNavStatus.Started
                    personalInfoViewModel.starListeningPersonalInfoDB()
                }

                else -> {

                }
            }

            PersonalInfoDataInitScreenRoot(
                personalInfoViewModel,
                navMainController
            )
        }
    }
}