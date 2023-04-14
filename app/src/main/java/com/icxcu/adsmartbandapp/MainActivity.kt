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
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.screens.*
import com.icxcu.adsmartbandapp.screens.plotsFields.BloodPressureInfo
import com.icxcu.adsmartbandapp.screens.plotsFields.PhysicalActivityInfo
import com.icxcu.adsmartbandapp.ui.theme.ADSmartBandAppTheme
import com.icxcu.adsmartbandapp.viewModels.*
import java.text.SimpleDateFormat
import java.util.*

const val REQUEST_ENABLE_BT: Int = 500

class MainActivity : ComponentActivity() {
    private lateinit var dViewModel: DataViewModel
    private lateinit var bluetoothLEManager: BluetoothManager
    private lateinit var mViewModel: BluetoothScannerViewModel
    private lateinit var pViewModel: PermissionsViewModel
    private lateinit var startDestination:String


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

    private var askPermissions = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ADSmartBandAppTheme {
                val owner = LocalViewModelStoreOwner.current

                owner?.let {
                    mViewModel = viewModel(
                        it,
                        "MainViewModel",
                        BluetoothScannerViewModelFactory(
                            LocalContext.current.applicationContext
                                    as Application
                        )
                    )

                    pViewModel = viewModel(
                        it,
                        "PermissionsViewModel",
                        PermissionsViewModelFactory()
                    )

                    dViewModel = viewModel(it,
                        "DataViewModel",
                        DataViewModelFactory(LocalContext.current.applicationContext
                                as Application))


                }

                //SetPermissions(this@MainActivity, permissionsRequired)
                for (permission in permissionsRequired) {
                    if (ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            permission
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        askPermissions.add(permission)
                    }
                }

                // if the list if empty, all permissions are granted
                startDestination = if (askPermissions.isEmpty()) {
                    Routes.BluetoothScanner.route
                } else {
                    Routes.Permissions.route
                }
                bluetoothLEManager = BluetoothLEManagerImp(this@MainActivity, mViewModel)
                MainContent()
            }
        }
    }


    override fun onRestart() {
        super.onRestart()
        setContent {
            ADSmartBandAppTheme {
                MainContent()
            }
        }
    }


    @Composable
    private fun MainContent( ){

        Log.d("TAG", "MainContent: ")

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {

            val navMainController = rememberNavController()

            val navLambdaDataScreen = { name: String, address: String ->
                if (mViewModel.liveStatusResults == 1 || mViewModel.liveStatusResults == -1) {
                    navMainController.navigate(
                        Routes.DataHome
                            .route + "/${name}/${address}"
                    )
                }
            }

            val navLambdaToBlueScanScreen = {
                navMainController.navigate(Routes.BluetoothScanner.route){
                    popUpTo(navMainController.graph.id){
                        inclusive=true
                    }
                }
            }

            val navLambdaBackBluetoothScanner = {
                navMainController.popBackStack()
                mViewModel.liveBasicBluetoothAdapter.value = mutableListOf()
                mViewModel.liveStatusResults = -2
            }

            val navLambdaBackDataHome = {
                navMainController.popBackStack()
            }

            val basicBluetoothAdapters by mViewModel
                .liveBasicBluetoothAdapter
                .observeAsState(listOf())


            NavHost(
                navController = navMainController,
                startDestination = startDestination
            ) {

                composable(Routes.Permissions.route) {
                    PermissionsScreen(
                        activity = this@MainActivity,
                        viewModel = pViewModel, navLambda = navLambdaToBlueScanScreen
                    )

                }

                composable(Routes.BluetoothScanner.route) {
                    BluetoothScanScreen(
                        basicBluetoothAdapters = basicBluetoothAdapters,
                        statusResultState = mViewModel.liveStatusResults,
                        mViewModel.leScanCallback,
                        bluetoothLEManager,
                        this@MainActivity,
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
                ) { backStackEntry ->

                    // Extracting exact values and passing it to Profile() screen
                    val bluetoothName = backStackEntry.arguments?.getString("bluetoothName")
                    val bluetoothAddress =
                        backStackEntry.arguments?.getString("bluetoothAddress")
                    dViewModel.macAddress=bluetoothAddress ?: "no address"
                    dViewModel.name=bluetoothName ?: "no name"

                    LaunchedEffect(key1 = true){
                        val physicalActivity = PhysicalActivity().apply {
                            macAddress=dViewModel.macAddress
                            val date = Date()
                            val formattedDate =  SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            dateData= formattedDate.format(date)
                            data= randomData()
                        }
                        dViewModel.insertPhysicalActivityData(physicalActivity)
                    }


                    DashBoard(
                        bluetoothName = bluetoothName ?: "no name",
                        bluetoothAddress = bluetoothAddress ?: "no address",
                        values = dViewModel.values,
                        navMainController = navMainController
                    ) { navLambdaBackBluetoothScanner() }
                }

                composable(Routes.StepsPlots.route){
                    val todayDate = Date()
                    val formattedDate =  SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val todayDateData= formattedDate.format(todayDate)
                    dViewModel.getDayPhysicalActivityData(todayDateData, dViewModel.macAddress, )
                    PhysicalActivityInfo(values = dViewModel.values,
                        dataViewModel = dViewModel
                    ){ navLambdaBackDataHome() }
                }

                composable(Routes.BloodPressurePlots.route){
                    BloodPressureInfo(values = dViewModel.values,){ navLambdaBackDataHome() }
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