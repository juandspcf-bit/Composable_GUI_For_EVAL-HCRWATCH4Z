package com.icxcu.adsmartbandapp.screens

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.icxcu.adsmartbandapp.viewModels.PermissionsViewModel


@Composable
fun PermissionsScreen(
    activity: Activity?,
    viewModel: PermissionsViewModel,
    navLambda: () ->Unit
) {

    var allPermissionsGranted by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp), verticalArrangement = Arrangement.Top
    ) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            viewModel.permissionAccessFineLocationGranted =
                isPermissionGranted(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )

            PermissionAccessFineLocationType(
                message = "Permission to scan Bluetooth devices to know its physical location",
                permissionType = Manifest.permission.ACCESS_FINE_LOCATION,
                activity = activity,
                viewModel
            )

            allPermissionsGranted=viewModel.permissionAccessFineLocationGranted


        }




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            viewModel.permissionAccessFineLocationGranted =
                isPermissionGranted(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            PermissionAccessFineLocationType(
                message = "Permission to scan Bluetooth devices to know its physical location",
                permissionType = Manifest.permission.ACCESS_FINE_LOCATION,
                activity = activity,
                viewModel
            )
            viewModel.permissionBluetoothConnectGranted =
                isPermissionGranted(
                    activity,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            PermissionBluetoothConnectType(
                message = "Permission to communicate with already-paired Bluetooth devices",
                permissionType = Manifest.permission.BLUETOOTH_CONNECT,
                activity = activity,
                viewModel
            )

            allPermissionsGranted=viewModel.permissionAccessFineLocationGranted
                    && viewModel.permissionBluetoothConnectGranted
        }

        ElevatedButton(
            enabled = allPermissionsGranted,
            modifier = Modifier
                .padding(5.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
            onClick = {
                if (isPermissionGranted(
                        activity,
                        Manifest.permission.BLUETOOTH_CONNECT
                    )
                ){
                    Toast.makeText(activity, "permission granted",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(activity, "permission not granted",Toast.LENGTH_LONG).show()
                }
                navLambda()
            }
        ) {
            Text(text = "scan devices")
        }


    }


}


@Composable
fun PermissionAccessFineLocationType(
    message: String = "",
    permissionType: String,
    activity: Activity?,
    viewModel: PermissionsViewModel
) {

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { permissionGranted_ ->
            // this is called when the user selects allow or deny
            Toast.makeText(
                activity,
                "permissionGranted_ $permissionGranted_",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.permissionAccessFineLocationGranted = permissionGranted_
        }



    Row() {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .weight(0.7f)
                .padding(7.dp)
        )

        ElevatedButton(
            enabled = !viewModel.permissionAccessFineLocationGranted,
            modifier = Modifier
                .weight(0.3f)
                .padding(5.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
            onClick = {
                if (!viewModel.permissionAccessFineLocationGranted) {
                    permissionLauncher.launch(permissionType)
                }
            }
        ) {
            Text(text = "request")
        }
    }
}


@Composable
fun PermissionBluetoothConnectType(
    message: String = "",
    permissionType: String,
    activity: Activity?,
    viewModel: PermissionsViewModel
) {

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { permissionGranted_ ->
            // this is called when the user selects allow or deny
            Toast.makeText(
                activity,
                "permissionGranted_ $permissionGranted_",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.permissionBluetoothConnectGranted = permissionGranted_
        }



    Row() {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .weight(0.7f)
                .padding(7.dp)
        )

        ElevatedButton(
            enabled = !viewModel.permissionBluetoothConnectGranted,
            modifier = Modifier
                .weight(0.3f)
                .padding(5.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
            onClick = {
                if (!viewModel.permissionBluetoothConnectGranted) {
                    permissionLauncher.launch(permissionType)
                }
            }
        ) {
            Text(text = "request")
        }
    }
}

fun isPermissionGranted(activity: Activity?, permissionType: String): Boolean {
    return activity?.let {
        ContextCompat.checkSelfPermission(
            it,
            permissionType
        )
    } == PackageManager.PERMISSION_GRANTED
}

@Preview(showBackground = true)
@Composable
fun PermissionsScreenPreview() {
    //PermissionsScreen(null,)
}