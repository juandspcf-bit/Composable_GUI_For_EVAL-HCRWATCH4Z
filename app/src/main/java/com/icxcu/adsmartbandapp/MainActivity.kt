package com.icxcu.adsmartbandapp

import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.icxcu.adsmartbandapp.bluetooth.BluetoothLEManagerImp
import com.icxcu.adsmartbandapp.bluetooth.BluetoothManager
import com.icxcu.adsmartbandapp.data.local.dataPrefrerences.PreferenceDataStoreHelper
import com.icxcu.adsmartbandapp.repositories.Values
import com.icxcu.adsmartbandapp.screens.BluetoothListScreenNavigationStatus
import com.icxcu.adsmartbandapp.screens.BluetoothScanScreen
import com.icxcu.adsmartbandapp.screens.PermissionsScreen
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.mainNavBar.RootMainNavigationBar
import com.icxcu.adsmartbandapp.screens.mainNavBar.SWReadingStatus
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.InvalidAlertDialogState
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.PersonalDataForm
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.PersonalInfoDataState
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.PersonalInfoInitDBHandlerAD
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.UpdateAlertDialogState
import com.icxcu.adsmartbandapp.screens.personalnfoInit.PersonalInfoInit
import com.icxcu.adsmartbandapp.screens.personalnfoInit.PersonalInfoInitDBHandler
import com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure.BloodPressureInfo
import com.icxcu.adsmartbandapp.screens.plotsFields.heartRate.HeartRateInfo
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.PhysicalActivityRoot
import com.icxcu.adsmartbandapp.screens.progressLoading.CircularProgressLoading
import com.icxcu.adsmartbandapp.ui.theme.ADSmartBandAppTheme
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModel
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import com.icxcu.adsmartbandapp.viewModels.DataViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.PermissionsViewModel
import com.icxcu.adsmartbandapp.viewModels.PermissionsViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.ScanningBluetoothAdapterStatus
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel
import com.icxcu.adsmartbandapp.viewModels.permissionsRequired
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val REQUEST_ENABLE_BT: Int = 500

class MainActivity : ComponentActivity() {
    private lateinit var dataViewModel: DataViewModel
    private lateinit var bluetoothLEManager: BluetoothManager
    private lateinit var bluetoothScannerViewModel: BluetoothScannerViewModel
    private lateinit var permissionsViewModel: PermissionsViewModel
    private val splashViewModel: SplashViewModel by viewModels()
    private lateinit var startDestination: String
    private lateinit var preferenceDataStoreHelper: PreferenceDataStoreHelper


    private var askPermissions = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            preferenceDataStoreHelper = PreferenceDataStoreHelper(this)
            splashViewModel.startDelay(preferenceDataStoreHelper)
            splashViewModel.stateFlow.value
        }

        setContent {
            ADSmartBandAppTheme {
                val owner = LocalViewModelStoreOwner.current

                owner?.let {
                    bluetoothScannerViewModel = viewModel(
                        it,
                        "BluetoothScannerViewModel",
                        BluetoothScannerViewModelFactory(
                            LocalContext.current.applicationContext
                                    as Application
                        )
                    )

                    permissionsViewModel = viewModel(
                        it,
                        "PermissionsViewModel",
                        PermissionsViewModelFactory()
                    )

                    dataViewModel = viewModel(
                        it,
                        "DataViewModel",
                        DataViewModelFactory(
                            LocalContext.current.applicationContext
                                    as Application
                        )
                    )


                }

                for (permission in permissionsRequired) {
                    if (ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            permission
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        askPermissions.add(permission)
                    }
                }

                startDestination = if (askPermissions.isNotEmpty()) {
                    Routes.Permissions.route
                } else if (splashViewModel.route.isNotEmpty() && splashViewModel.route[1] == Routes.BluetoothScanner.route) {
                    Log.d("Route", "onCreate: Routes.BluetoothScanner.route")
                    Routes.BluetoothScanner.route
                } else if (splashViewModel.route.isNotEmpty() && splashViewModel.route[1] == Routes.DataHome.route) {
                    Log.d("Route", "onCreate: Routes.DataHome.route")
                    Routes.DataHome.route
                } else {
                    Log.d("Route", "onCreate:default")
                    Routes.CircularProgressLoading.route
                }


                bluetoothLEManager =
                    BluetoothLEManagerImp(this@MainActivity, bluetoothScannerViewModel)
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
    private fun MainContent() {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            val navMainController = rememberNavController()

            val navLambdaToMainNavigationBar = remember(bluetoothScannerViewModel, navMainController) {
                { name: String, address: String ->
                    if (bluetoothScannerViewModel.scanningBluetoothAdaptersStatus == ScanningBluetoothAdapterStatus.SCANNING_FINISHED_WITH_RESULTS
                        || bluetoothScannerViewModel.scanningBluetoothAdaptersStatus == ScanningBluetoothAdapterStatus.SCANNING_FORCIBLY_STOPPED) {
                        Log.d(
                            "DATAX",
                            "MainContent-1: ${dataViewModel.smartWatchState.todayDateValuesReadFromSW.stepList.sum()}"
                        )

                        navMainController.navigate(
                            Routes.DataHomeFromBluetoothScannerScreen
                                .route + "/${name}/${address}"
                        )

                        if(bluetoothScannerViewModel.bluetoothAdaptersList.isEmpty()){
                            bluetoothScannerViewModel.scanningBluetoothAdaptersStatus = ScanningBluetoothAdapterStatus.NO_SCANNING_WELCOME_SCREEN
                        }else{
                            bluetoothScannerViewModel.scanningBluetoothAdaptersStatus = ScanningBluetoothAdapterStatus.NO_SCANNING_WITH_RESULTS
                        }

                    }
                }
            }


            val navLambdaToBlueScannerScreen = remember(navMainController) {
                {
                    navMainController.navigate(Routes.BluetoothScanner.route) {
                        popUpTo(navMainController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            }


            val navLambdaBackToBluetoothScanner =
                remember(bluetoothScannerViewModel, navMainController) {
                    {
                        navMainController.popBackStack()
                        bluetoothScannerViewModel.bluetoothAdaptersList = mutableListOf()
                        bluetoothScannerViewModel.scanningBluetoothAdaptersStatus =
                            ScanningBluetoothAdapterStatus.NO_SCANNING_WELCOME_SCREEN
                    }
                }

            val navLambdaBackToMainNavigationBar = remember {
                {
                    navMainController.popBackStack()
                }
            }

            val getFetchingDataFromSWStatus = {
                dataViewModel.smartWatchState.fetchingDataFromSWStatus
            }

            val setFetchingDataFromSWStatusSTOPPED = {
                Log.d("DATAX", "MainContent setFetchingDataFromSWStatusSTOPPED STOPPED")

                dataViewModel.stateBluetoothListScreenNavigationStatus =
                    BluetoothListScreenNavigationStatus.IN_PROGRESS_TO_MAIN_NAV_SCREEN
                dataViewModel.smartWatchState.fetchingDataFromSWStatus = SWReadingStatus.STOPPED
            }

            val getLiveBasicBluetoothAdapterList = remember(bluetoothScannerViewModel) {
                {
                    bluetoothScannerViewModel.bluetoothAdaptersList
                }
            }

            val setLiveBasicBluetoothAdapterList = remember(bluetoothScannerViewModel) {
                {
                    bluetoothScannerViewModel.bluetoothAdaptersList = mutableListOf()
                    bluetoothScannerViewModel.partialList = mutableListOf()
                }
            }

            val getScanningBluetoothAdaptersStatus = remember(bluetoothScannerViewModel) {
                {
                    bluetoothScannerViewModel.scanningBluetoothAdaptersStatus
                }
            }

            NavHost(
                navController = navMainController,
                startDestination = startDestination
            ) {

                composable(Routes.CircularProgressLoading.route) {
                    CircularProgressLoading()
                }

                composable(Routes.Permissions.route) {
                    PermissionsScreen(
                        activity = this@MainActivity,
                        viewModel = permissionsViewModel, navLambda = navLambdaToBlueScannerScreen
                    )
                }

                composable(Routes.BluetoothScanner.route) {
                    Log.d("DATAX", "Routes.BluetoothScanner.route: ENTER")

                    when (dataViewModel.stateBluetoothListScreenNavigationStatus) {
                        BluetoothListScreenNavigationStatus.IN_PROGRESS_TO_BLUETOOTH_SCREEN -> {
                            clearStateSW(dataViewModel)
                        }

                        BluetoothListScreenNavigationStatus.IN_PROGRESS_TO_MAIN_NAV_SCREEN -> {
                            Log.d("DATAX", "Routes.BluetoothScanner.route: CLEARED")
                        }
                    }

                    BluetoothScanScreen(
                        getLiveBasicBluetoothAdapterList,
                        setLiveBasicBluetoothAdapterList,
                        getScanningBluetoothAdaptersStatus,
                        bluetoothScannerViewModel.leScanCallback,
                        bluetoothLEManager,
                        this@MainActivity,
                        setFetchingDataFromSWStatusSTOPPED,
                        navLambdaToMainNavigationBar
                    )
                }

                composable(
                    Routes.DataHomeFromBluetoothScannerScreen.route + "/{bluetoothName}/{bluetoothAddress}",                    // declaring placeholder in String route
                    arguments = listOf(
                        // declaring argument type
                        navArgument("bluetoothName") { type = NavType.StringType },
                        navArgument("bluetoothAddress") { type = NavType.StringType },
                    )
                ) { backStackEntry ->

                    Log.d("DATAX", "Routes.DataHomeFromBluetoothScannerScreen.route ENTERED")

                    val bluetoothName =
                        backStackEntry.arguments?.getString("bluetoothName") ?: "no name"
                    val bluetoothAddress =
                        backStackEntry.arguments?.getString("bluetoothAddress") ?: "no address"

                    splashViewModel.writeDataPreferences(
                        preferenceDataStoreHelper,
                        name = bluetoothName,
                        address = bluetoothAddress,
                    )

                    bluetoothScannerViewModel.bluetoothAdaptersList = mutableListOf()
                    bluetoothScannerViewModel.scanningBluetoothAdaptersStatus =
                        ScanningBluetoothAdapterStatus.NO_SCANNING_WELCOME_SCREEN
                    RootMainNavigationBar(
                        dataViewModel,
                        getFetchingDataFromSWStatus,
                        bluetoothAddress,
                        bluetoothName,
                        navLambdaBackToBluetoothScanner,
                        navMainController,
                        splashViewModel
                    )
                }

                composable(
                    Routes.DataHome.route,                    // declaring placeholder in String route
                ) {
                    Log.d("DATAX", "Routes.DataHome.route: ENTER")

                    val bluetoothName = splashViewModel.route[2]
                    val bluetoothAddress = splashViewModel.route[3]

                    bluetoothScannerViewModel.bluetoothAdaptersList = mutableListOf()
                    bluetoothScannerViewModel.scanningBluetoothAdaptersStatus =
                        ScanningBluetoothAdapterStatus.NO_SCANNING_WELCOME_SCREEN
                    RootMainNavigationBar(
                        dataViewModel,
                        getFetchingDataFromSWStatus,
                        bluetoothAddress,
                        bluetoothName,
                        navLambdaBackToBluetoothScanner,
                        navMainController,
                        splashViewModel
                    )

                }

                composable(Routes.StepsPlots.route) {
                    Log.d("DATAX", "MainContentStepsPlots:")
                    val myDateObj = LocalDateTime.now()
                    val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val formattedDate = myDateObj.format(myFormatObj)
                    dataViewModel.getDayPhysicalActivityData(
                        formattedDate,
                        dataViewModel.macAddressDeviceBluetooth
                    )
                    PhysicalActivityRoot(
                        dataViewModel = dataViewModel
                    ) { navLambdaBackToMainNavigationBar() }
                }

                composable(Routes.BloodPressurePlots.route) {
                    val myDateObj = LocalDateTime.now()
                    val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val formattedDate = myDateObj.format(myFormatObj)
                    dataViewModel.getDayBloodPressureData(
                        formattedDate,
                        dataViewModel.macAddressDeviceBluetooth
                    )
                    BloodPressureInfo(dataViewModel = dataViewModel) {
                        navLambdaBackToMainNavigationBar()
                    }
                }

                composable(Routes.HeartRatePlot.route) {
                    val myDateObj = LocalDateTime.now()
                    val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val formattedDate = myDateObj.format(myFormatObj)
                    dataViewModel.getDayHeartRateData(
                        formattedDate,
                        dataViewModel.macAddressDeviceBluetooth
                    )
                    HeartRateInfo(dataViewModel = dataViewModel) {
                        navLambdaBackToMainNavigationBar()
                    }
                }

                composable(Routes.PersonalInfoForm.route) {
                    PersonalInfoInitDBHandlerAD(
                        dataViewModel
                    )
                    PersonalDataForm(dataViewModel = dataViewModel) {
                        navLambdaBackToMainNavigationBar()
                    }
                }

                composable(Routes.PersonalInfoFormInit.route) {
                    dataViewModel.getPersonalInfoData(dataViewModel.macAddressDeviceBluetooth)
                    PersonalInfoInitDBHandler(
                        dataViewModel
                    )
                    PersonalInfoInit(dataViewModel = dataViewModel) {
                        navLambdaToBlueScannerScreen()
                    }
                }

            }


        }
    }


    private fun clearStateSW(dataViewModel: DataViewModel) {

        val myFormatObj:DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val todayLocalDateTime:LocalDateTime = LocalDateTime.now()
        val todayFormattedDate:String = todayLocalDateTime.format(myFormatObj)
        dataViewModel.todayFormattedDate = todayFormattedDate

        val yesterdayLocalDateTime:LocalDateTime = todayLocalDateTime.minusDays(1)
        val yesterdayFormattedDate:String = yesterdayLocalDateTime.format(myFormatObj)
        dataViewModel.yesterdayFormattedDate = yesterdayFormattedDate

        val pastYesterdayLocalDateTime:LocalDateTime = todayLocalDateTime.minusDays(2)
        val pastYesterdayFormattedDate:String = pastYesterdayLocalDateTime.format(myFormatObj)
        dataViewModel.pastYesterdayFormattedDate = pastYesterdayFormattedDate

        dataViewModel.smartWatchState.progressbarForFetchingDataFromSW = false
        dataViewModel.smartWatchState.fetchingDataFromSWStatus = SWReadingStatus.CLEARED


        dataViewModel.smartWatchState.todayDateValuesReadFromSW =
            Values(
                MutableList(48) { 0 }.toList(),
                MutableList(48) { 0.0 }.toList(),
                MutableList(48) { 0.0 }.toList(),
                MutableList(48) { 0.0 }.toList(),
                MutableList(48) { 0.0 }.toList(),
                MutableList(48) { 0.0 }.toList(),
                dataViewModel.todayFormattedDate
            )

        dataViewModel.smartWatchState.yesterdayDateValuesFromSW =
            Values(
                MutableList(48) { 0 }.toList(),
                MutableList(48) { 0.0 }.toList(),
                MutableList(48) { 0.0 }.toList(),
                MutableList(48) { 0.0 }.toList(),
                MutableList(48) { 0.0 }.toList(),
                MutableList(48) { 0.0 }.toList(),
                dataViewModel.todayFormattedDate
            )

        dataViewModel.swRepository.jobSW.cancel()

        dataViewModel.collecDataScope.cancel()
        dataViewModel.selectedDay = ""

        dataViewModel.statusStartedReadingDataLasThreeDaysData = false



        dataViewModel.personalInfoFromDB.value = listOf()
        dataViewModel.personalInfoListReadFromDB = listOf()

        dataViewModel.personalInfoDataState = PersonalInfoDataState()
        dataViewModel.invalidAlertDialogState = InvalidAlertDialogState()
        dataViewModel.updateAlertDialogState = UpdateAlertDialogState()

        dataViewModel.macAddressDeviceBluetooth = ""
        dataViewModel.nameDeviceBluetooth = ""
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ADSmartBandAppTheme {

    }
}