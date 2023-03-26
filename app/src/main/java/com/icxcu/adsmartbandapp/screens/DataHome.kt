package com.icxcu.adsmartbandapp.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.icxcu.adsmartbandapp.MainActivity
import com.icxcu.adsmartbandapp.bluetooth.device.DeviceConnection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataHome(bluetoothName: String,
             bluetoothAddress: String,
             mainActivity: MainActivity?,
            deviceConnection: DeviceConnection?,
             navLambda: () -> Unit) {



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text="$bluetoothName, $bluetoothAddress ", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(mainActivity, "Back Icon Click", Toast.LENGTH_SHORT)
                            .show()
                        navLambda()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xff1d2a35),
                )
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ListFields()
            }

        },


        )


}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DataHome(
        bluetoothName = "ddd",
        bluetoothAddress = "ddddd",
        mainActivity = null,
        null,
        {}
    )
}