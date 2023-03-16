package com.icxcu.adsmartbandapp

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.icxcu.adsmartbandapp.bluetooth.BluetoothLEManagerImp
import com.icxcu.adsmartbandapp.screens.BluetoothScanScreen
import com.icxcu.adsmartbandapp.ui.theme.ADSmartBandAppTheme
import com.icxcu.adsmartbandapp.viewModels.MainViewModel
import com.icxcu.adsmartbandapp.viewModels.MainViewModelFactory

const val REQUEST_ENABLE_BT: Int = 500

class MainActivity : ComponentActivity() {

    private lateinit var mViewModel: MainViewModel

    private val RECORD_REQUEST_CODE = 101
    private fun makeRequestCoarse() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            RECORD_REQUEST_CODE
        )
    }

    private fun makeRequestBluetoothConnect() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                RECORD_REQUEST_CODE
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionCoarse = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (permissionCoarse != PackageManager.PERMISSION_GRANTED) {
            Log.i("TAG", "Permission to location")
            makeRequestCoarse()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val permissionConnect = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            )

            if (permissionConnect != PackageManager.PERMISSION_GRANTED) {
                Log.i("TAG", "Permission to connect")
                makeRequestBluetoothConnect()
            }
        }


        setContent {
            ADSmartBandAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val owner = LocalViewModelStoreOwner.current

                    owner?.let {
                        mViewModel = viewModel(
                            it,
                            "MainViewModel",
                            MainViewModelFactory(
                                LocalContext.current.applicationContext
                                        as Application
                            )
                        )
                    }

                    BluetoothScanScreen(
                        mViewModel,
                        BluetoothLEManagerImp(this@MainActivity, mViewModel )
                    )
                }
            }
        }
    }


}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ADSmartBandAppTheme {

    }
}