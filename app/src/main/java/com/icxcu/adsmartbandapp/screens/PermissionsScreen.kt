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


@Composable
fun PermissionsScreen(activity: Activity?) {
    var allPermissionAllowed by remember {mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp), verticalArrangement = Arrangement.Top
    ) {


        var permission1Granted by remember {
            mutableStateOf(isPermissionGranted(activity, Manifest.permission.ACCESS_FINE_LOCATION))
        }
        PermissionType(
            message = "Permission to scan Bluetooth devices to know its physical location",
            permissionType = Manifest.permission.ACCESS_FINE_LOCATION,
            activity = activity,
            permission1Granted
        )


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            var permission2Granted by remember {
                mutableStateOf(isPermissionGranted(activity, Manifest.permission.BLUETOOTH_CONNECT))
            }
            PermissionType(
                message = "Permission to communicate with already-paired Bluetooth devices",
                permissionType = Manifest.permission.BLUETOOTH_CONNECT,
                activity = activity,
                permission2Granted
            )
        }


    }


}



@Composable
fun PermissionType(message: String = "", permissionType: String, activity: Activity?, permissionGranted:Boolean) {

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { permissionGranted_ ->
            // this is called when the user selects allow or deny
            Toast.makeText(
                activity,
                "permissionGranted_ $permissionGranted_",
                Toast.LENGTH_SHORT
            ).show()
            //permissionGranted = permissionGranted_
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
            modifier = Modifier
                .weight(0.3f)
                .padding(5.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
            onClick = {
                if (!permissionGranted) {
                    permissionLauncher.launch(permissionType)
                }
            }
        ) {
            Text(text = "request")
        }
    }
}

private fun isPermissionGranted(activity: Activity?, permissionType: String): Boolean {
    return activity?.let {
        ContextCompat.checkSelfPermission(
            it,
            permissionType        )
    } == PackageManager.PERMISSION_GRANTED
}

@Preview(showBackground = true)
@Composable
fun PermissionsScreenPreview() {
    PermissionsScreen(null)
}