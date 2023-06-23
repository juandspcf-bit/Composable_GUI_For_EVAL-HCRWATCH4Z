package com.icxcu.adsmartbandapp.screens.viewModelProviders

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.icxcu.adsmartbandapp.viewModels.BloodPressureViewModel
import com.icxcu.adsmartbandapp.viewModels.BloodPressureViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModel
import com.icxcu.adsmartbandapp.viewModels.HeartRateViewModel
import com.icxcu.adsmartbandapp.viewModels.HeartRateViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.MainNavigationModelFactory
import com.icxcu.adsmartbandapp.viewModels.MainNavigationViewModel
import com.icxcu.adsmartbandapp.viewModels.PhysicalActivityViewModel
import com.icxcu.adsmartbandapp.viewModels.PhysicalActivityViewModelFactory

@Composable
inline fun <reified S : ViewModel, T> NavBackStackEntry.scopedViewModel(
    navController: NavController,
    key: String,
    factory: T
): S {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    val viewModel: S?

    viewModel = viewModel(
        parentEntry,
        key,
        factory as ViewModelProvider.Factory?,
    )
    return viewModel
}
