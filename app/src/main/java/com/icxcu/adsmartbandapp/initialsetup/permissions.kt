package com.icxcu.adsmartbandapp.initialsetup

import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import com.icxcu.adsmartbandapp.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SetPermissions(mainActivity: MainActivity, permissionsRequired: List<String>){
    var allPermissionGranted by remember {
        mutableStateOf(false)
    }
    var askPermissions by remember {
        mutableStateOf(arrayListOf<String>())
    }

    val permissionsLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { permissionsMap ->

            Toast.makeText(
                mainActivity,
                "permissionsMap $permissionsMap",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("Permissions", "onCreate: $permissionsMap")

            // if the map does NOT contain false,
            // all the permissions are granted
            Log.d("Permissions", "SetPermissions: $permissionsMap")
            allPermissionGranted = !permissionsMap.containsValue(false)
        }

    // add the permissions that are NOT granted
    for (permission in permissionsRequired) {
        if (ContextCompat.checkSelfPermission(
                mainActivity,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askPermissions.add(permission)
        }
    }

    // if the list if empty, all permissions are granted
    allPermissionGranted = askPermissions.isEmpty()

    val scope = CoroutineScope(Dispatchers.Main)

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            delay(100)
            if (!allPermissionGranted) {
                // ask for permission
                permissionsLauncher.launch(askPermissions.toTypedArray())
            }


        }
    }

}