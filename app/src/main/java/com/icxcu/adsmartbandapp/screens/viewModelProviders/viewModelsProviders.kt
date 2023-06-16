package com.icxcu.adsmartbandapp.screens.viewModelProviders

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.icxcu.adsmartbandapp.viewModels.PersonalInfoViewModel
import com.icxcu.adsmartbandapp.viewModels.PersonalInfoViewModelFactory

@Composable
inline fun<reified T: ViewModel> NavBackStackEntry.personalInfoViewModel(navController: NavController): T{
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember (this){
        navController.getBackStackEntry(navGraphRoute)
    }

    val personalInfoViewModel: PersonalInfoViewModel?

    personalInfoViewModel = viewModel(
        parentEntry,
        "PersonalInfoViewModel",
        PersonalInfoViewModelFactory(
            LocalContext.current.applicationContext
                    as Application
        ),

        )
    return personalInfoViewModel as T
}