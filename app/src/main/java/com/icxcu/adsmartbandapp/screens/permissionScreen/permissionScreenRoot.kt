package com.icxcu.adsmartbandapp.screens.permissionScreen

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.icxcu.adsmartbandapp.screens.BluetoothScannerNestedRoute
import com.icxcu.adsmartbandapp.screens.PersonalInfoInitNestedRoute
import com.icxcu.adsmartbandapp.screens.PersonalInfoNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.viewModels.PermissionsViewModel

@Composable
fun PermissionsScreenRoot(
    activity: Activity?,
    permissionsViewModel: PermissionsViewModel,
    navMainController:NavController
){

    val navLambdaToBlueScannerScreen = remember(navMainController) {
        {
            navMainController.navigate( PersonalInfoInitNestedRoute.PersonalInfoInitMainRoute().route) {
                popUpTo(0) {
                    inclusive = true
                }
            }
        }
    }

    PermissionsScreen(
        activity = activity,
        viewModel = permissionsViewModel,
        navLambda = navLambdaToBlueScannerScreen
    )

}