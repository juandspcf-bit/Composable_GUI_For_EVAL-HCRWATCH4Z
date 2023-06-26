package com.icxcu.adsmartbandapp.screens.progressLoading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.screens.BluetoothScannerNestedRoute
import com.icxcu.adsmartbandapp.screens.MainNavigationNestedRoute
import com.icxcu.adsmartbandapp.screens.PersonalInfoInitNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.viewModels.CircularProgressViewModel
import com.icxcu.adsmartbandapp.viewModels.SharedViewModel
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun CircularProgressLoading(
    navController: NavHostController,
    askPermissions: ArrayList<String>,
    circularProgressViewModel: CircularProgressViewModel,
    splashViewModel: SharedViewModel
) {

    Box(
        modifier = Modifier
            .background(Color(0xff1d2a35))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){

        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            color = Color(0xFFDCE775),
            strokeWidth = 20.dp
        )

        LaunchedEffect(key1 = true, ){


            delay(500)
            if (askPermissions.isNotEmpty()) {
                navController.navigate(Routes.Permissions.route)
            }else if(askPermissions.isEmpty() && circularProgressViewModel.personalInfoDataStateC[0].id==-1){
                navController.navigate(PersonalInfoInitNestedRoute.PersonalInfoInitMainRoute().route)
            }else if (splashViewModel.lastSelectedBluetoothDeviceAddress.size==2){
                navController.navigate(BluetoothScannerNestedRoute.BluetoothScannerScreen().route)
            }else{
                navController.navigate(MainNavigationNestedRoute.MainNavigationMainRoute().route)
            }
        }

    }


}

sealed class CircularProgressScreenNavStatus{
    object Started: CircularProgressScreenNavStatus()
    object Leaving: CircularProgressScreenNavStatus()
}