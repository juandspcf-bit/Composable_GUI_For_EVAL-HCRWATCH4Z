package com.icxcu.adsmartbandapp.screens.progressLoading

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.REQUEST_ENABLE_BT
import com.icxcu.adsmartbandapp.screens.BluetoothScannerNestedRoute
import com.icxcu.adsmartbandapp.screens.MainNavigationNestedRoute
import com.icxcu.adsmartbandapp.screens.PersonalInfoInitNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.viewModels.CircularProgressViewModel
import com.icxcu.adsmartbandapp.viewModels.SharedViewModel
import kotlinx.coroutines.delay

@Composable
fun CircularProgressLoading(
    navController: NavHostController,
    askPermissions: ArrayList<String>,
    circularProgressViewModel: CircularProgressViewModel,
    sharedViewModel: SharedViewModel
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
            }else if (sharedViewModel.lastSelectedBluetoothDeviceAddress.size==2){
                val bluetoothAdapter = circularProgressViewModel.bluetoothAdapter
                if (bluetoothAdapter.isEnabled) {
                    navController.navigate(BluetoothScannerNestedRoute.BluetoothScannerScreen().route)
                }else{
                    sharedViewModel.enableBluetoothPortNextRoute = BluetoothScannerNestedRoute.BluetoothScannerScreen().route
                    navController.navigate(Routes.EnableBluetoothPortScreen.route)
                }

            }else{
                val bluetoothAdapter = circularProgressViewModel.bluetoothAdapter
                if (bluetoothAdapter.isEnabled) {
                    navController.navigate(MainNavigationNestedRoute.MainNavigationMainRoute().route)
                }else{
                    sharedViewModel.enableBluetoothPortNextRoute = MainNavigationNestedRoute.MainNavigationMainRoute().route
                    navController.navigate(Routes.EnableBluetoothPortScreen.route)
                }

            }
        }

    }


}

@Composable
fun PleaseEnableBluetoothComposable(
    startForResult: ActivityResultLauncher<Intent>,
) {
    val activity = LocalContext.current as ComponentActivity

    Box(contentAlignment = Alignment.Center) {
        Text(
            "Enable Bluetooth",
            modifier = Modifier
                .align(Alignment.TopCenter),
            fontSize = 60.sp,
            color = Color.White
        )

        Icon(
            painter = painterResource(R.drawable.bluetooth_24px),
            contentDescription = "Date Range",
            tint = Color.White,
            modifier = Modifier
                .clickable {
                    enableBluetooth2(
                        activity,
                        startForResult
                    )
                }
                .fillMaxSize(0.3f)
        )

    }
}


fun enableBluetooth(
    activity: Activity,
) {
    val bluetoothManager: BluetoothManager? =
        ContextCompat.getSystemService(activity, BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter
    if (bluetoothAdapter?.isEnabled == false) {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        ActivityCompat.startActivityForResult(
            activity,
            enableBtIntent,
            REQUEST_ENABLE_BT,
            null
        )
    }
}

fun enableBluetooth2(
    activity: Activity,
    startForResult: ActivityResultLauncher<Intent>?,
) {
    val bluetoothManager: BluetoothManager? =
        ContextCompat.getSystemService(activity, BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter
    if (bluetoothAdapter?.isEnabled == false) {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startForResult?.launch(enableBtIntent)

    }
}

sealed class CircularProgressScreenNavStatus{
    object Started: CircularProgressScreenNavStatus()
    object Leaving: CircularProgressScreenNavStatus()
}