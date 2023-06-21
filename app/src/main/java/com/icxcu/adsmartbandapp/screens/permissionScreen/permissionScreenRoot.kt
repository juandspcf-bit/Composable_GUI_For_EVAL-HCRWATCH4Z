package com.icxcu.adsmartbandapp.screens.permissionScreen

import android.Manifest
import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.icxcu.adsmartbandapp.screens.PersonalInfoInitNestedRoute
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

    var allPermissionsGranted by remember {
        mutableStateOf(false)
    }

    val permissionDataTypeList = mutableListOf<PermissionDataType>()

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
        permissionsViewModel.permissionAccessFineLocationGranted =
            isPermissionGranted(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

        val permissionAccessFineLocationLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { permissionGranted_ ->
                // this is called when the user selects allow or deny
                Toast.makeText(
                    activity,
                    "Permission Granted $permissionGranted_",
                    Toast.LENGTH_SHORT
                ).show()
                permissionsViewModel.permissionAccessFineLocationGranted = permissionGranted_
            }


        permissionDataTypeList.add(
            PermissionDataType(
                "Permission to scan Bluetooth devices to know its physical location",
                Manifest.permission.ACCESS_FINE_LOCATION,
                permissionAccessFineLocationLauncher,
                permissionsViewModel.permissionAccessFineLocationGranted
            )
        )

        allPermissionsGranted = permissionsViewModel.permissionAccessFineLocationGranted


    }


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

        permissionsViewModel.permissionAccessFineLocationGranted =
            isPermissionGranted(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )


        val permissionAccessFineLocationLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { permissionGranted_ ->

                // this is called when the user selects allow or deny
                Toast.makeText(
                    activity,
                    "Permission Granted $permissionGranted_",
                    Toast.LENGTH_SHORT
                ).show()
                permissionsViewModel.permissionAccessFineLocationGranted = permissionGranted_
            }

        permissionDataTypeList.add(
            PermissionDataType(
                message = "Permission to scan Bluetooth devices to know its physical location",
                permissionType = Manifest.permission.ACCESS_FINE_LOCATION,
                permissionAccessFineLocationLauncher,
                permissionsViewModel.permissionAccessFineLocationGranted
            )
        )



        permissionsViewModel.permissionBluetoothScanGranted =
            isPermissionGranted(
                activity,
                Manifest.permission.BLUETOOTH_SCAN
            )
        val permissionBluetoothScanLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { permissionGranted_ ->
                // this is called when the user selects allow or deny
                Toast.makeText(
                    activity,
                    "Permission Granted $permissionGranted_",
                    Toast.LENGTH_SHORT
                ).show()
                permissionsViewModel.permissionBluetoothScanGranted = permissionGranted_
            }

        permissionDataTypeList.add(
            PermissionDataType(
                message = "Permission to scan bluetooth devices",
                permissionType = Manifest.permission.BLUETOOTH_SCAN,
                permissionBluetoothScanLauncher,
                permissionsViewModel.permissionBluetoothScanGranted
            )
        )

        permissionsViewModel.permissionBluetoothConnectGranted =
            isPermissionGranted(
                activity,
                Manifest.permission.BLUETOOTH_CONNECT
            )
        val permissionBluetoothConnectLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { permissionGranted_ ->
                // this is called when the user selects allow or deny
                Toast.makeText(
                    activity,
                    "Permission Granted $permissionGranted_",
                    Toast.LENGTH_SHORT
                ).show()
                permissionsViewModel.permissionBluetoothConnectGranted = permissionGranted_
            }

        permissionDataTypeList.add(
            PermissionDataType(
                message = "Permission to communicate with already-paired Bluetooth devices",
                permissionType = Manifest.permission.BLUETOOTH_CONNECT,
                permissionBluetoothConnectLauncher,
                permissionsViewModel.permissionBluetoothConnectGranted
            )
        )


        allPermissionsGranted=permissionsViewModel.permissionAccessFineLocationGranted
                && permissionsViewModel.permissionBluetoothConnectGranted
                && permissionsViewModel.permissionBluetoothScanGranted
    }


    PermissionsScreen(
        activity,
        permissionDataTypeList,
        navLambdaToBlueScannerScreen,
        allPermissionsGranted,
    )

}

data class PermissionDataType(
    val message:String,
    val permissionType:String,
    val permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    val permissionGranted: Boolean
    )