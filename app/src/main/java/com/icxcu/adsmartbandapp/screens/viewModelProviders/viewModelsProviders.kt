package com.icxcu.adsmartbandapp.screens.viewModelProviders

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.icxcu.adsmartbandapp.viewModels.BloodPressureViewModel
import com.icxcu.adsmartbandapp.viewModels.BloodPressureViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.PersonalInfoViewModel
import com.icxcu.adsmartbandapp.viewModels.PersonalInformationViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.PhysicalActivityViewModel
import com.icxcu.adsmartbandapp.viewModels.PhysicalActivityViewModelFactory

@Composable
inline fun<reified T: ViewModel> NavBackStackEntry.personalInfoViewModel(navController: NavController): T{
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember (this){
        navController.getBackStackEntry(navGraphRoute)
    }

    val viewModel: PersonalInfoViewModel?

    viewModel = viewModel(
        parentEntry,
        "PersonalInfoViewModel",
        PersonalInformationViewModelFactory(
            LocalContext.current.applicationContext
                    as Application
        ),

        )
    return viewModel as T
}

@Composable
inline fun<reified T: ViewModel> NavBackStackEntry.physicalActivityViewModel(navController: NavController): T{
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember (this){
        navController.getBackStackEntry(navGraphRoute)
    }

    val viewModel: PhysicalActivityViewModel?

    viewModel = viewModel(
        parentEntry,
        "PhysicalActivityViewModel",
        PhysicalActivityViewModelFactory(
            LocalContext.current.applicationContext
                    as Application
        ),

        )
    return viewModel as T
}

@Composable
inline fun<reified T: ViewModel> NavBackStackEntry.bloodPressureViewModel(navController: NavController): T{
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember (this){
        navController.getBackStackEntry(navGraphRoute)
    }

    val viewModel: BloodPressureViewModel?

    viewModel = viewModel(
        parentEntry,
        "BloodPressureViewModel",
        BloodPressureViewModelFactory(
            LocalContext.current.applicationContext
                    as Application
        ),

        )
    return viewModel as T
}