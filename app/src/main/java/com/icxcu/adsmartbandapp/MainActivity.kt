package com.icxcu.adsmartbandapp

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.icxcu.adsmartbandapp.bluetooth.BluetoothLEManagerImp
import com.icxcu.adsmartbandapp.bluetooth.BluetoothManager
import com.icxcu.adsmartbandapp.initialsetup.SetPermissions
import com.icxcu.adsmartbandapp.screens.BluetoothScanScreen
import com.icxcu.adsmartbandapp.screens.DataHome
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.ui.theme.ADSmartBandAppTheme
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModel
import com.icxcu.adsmartbandapp.viewModels.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val REQUEST_ENABLE_BT: Int = 500

class MainActivity : ComponentActivity() {
    private lateinit var bluetoothLEManager: BluetoothManager
    private lateinit var mViewModel: BluetoothScannerViewModel

    private val permissionsRequired = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
        )
    } else {
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ADSmartBandAppTheme {


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


                    SetPermissions(this@MainActivity, permissionsRequired)


                    bluetoothLEManager = BluetoothLEManagerImp(this@MainActivity, mViewModel)
                    val navController = rememberNavController()
                    val navLambdaDataScreen = { name:String, address:String ->
                        if(mViewModel.liveStatusResults.value==1 || mViewModel.liveStatusResults.value==-1){
                            navController.navigate(Routes.DataHome
                                .route + "/${name}/${address}")
                        }
                    }

                    val navLambdaBackHome = {
                        navController.popBackStack()
                        mViewModel.liveBasicBluetoothAdapter.value = mutableListOf()
                        mViewModel.liveStatusResults.value = -2
                    }

                    val basicBluetoothAdapters by mViewModel
                        .liveBasicBluetoothAdapter
                        .observeAsState(listOf())

                    val statusResultState by mViewModel
                        .liveStatusResults
                        .observeAsState(-2)
                    NavHost(
                        navController = navController,
                        startDestination = Routes.BluetoothScanner.route
                    ) {
                        composable(Routes.BluetoothScanner.route) {
                            BluetoothScanScreen(
                                basicBluetoothAdapters =basicBluetoothAdapters,
                                statusResultState = statusResultState,
                                mViewModel.leScanCallback,
                                bluetoothLEManager,
                                navLambdaDataScreen
                            )
                        }

                        composable(
                            Routes.DataHome.route + "/{bluetoothName}/{bluetoothAddress}",                    // declaring placeholder in String route
                            arguments = listOf(
                                // declaring argument type
                                navArgument("bluetoothName") { type = NavType.StringType },
                                navArgument("bluetoothAddress") { type = NavType.StringType },
                            )
                        ) {
                                backStackEntry ->

                            // Extracting exact values and passing it to Profile() screen
                            val bluetoothName = backStackEntry.arguments?.getString("bluetoothName")
                            val bluetoothAddress = backStackEntry.arguments?.getString("bluetoothAddress")
                            DataHome(
                                bluetoothName = bluetoothName?:"no name",
                                bluetoothAddress = bluetoothAddress?:"no address",
                            this@MainActivity,
                                navLambda = { navLambdaBackHome() }
                            )
                        }
                        }



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