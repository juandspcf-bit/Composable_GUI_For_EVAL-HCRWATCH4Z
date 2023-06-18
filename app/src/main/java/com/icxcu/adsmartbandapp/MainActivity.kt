package com.icxcu.adsmartbandapp

import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.navigation
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.icxcu.adsmartbandapp.bluetooth.BluetoothLEManagerImp
import com.icxcu.adsmartbandapp.bluetooth.BluetoothManager
import com.icxcu.adsmartbandapp.data.local.dataPrefrerences.PreferenceDataStoreHelper
import com.icxcu.adsmartbandapp.repositories.Values
import com.icxcu.adsmartbandapp.screens.BloodPressureNestedRoute
import com.icxcu.adsmartbandapp.screens.BluetoothListScreenNavigationStatus
import com.icxcu.adsmartbandapp.screens.BluetoothScanScreen
import com.icxcu.adsmartbandapp.screens.HeartRateNestedRoute
import com.icxcu.adsmartbandapp.screens.MainNavigationNestedRoute
import com.icxcu.adsmartbandapp.screens.PhysicalActivityNestedRoute
import com.icxcu.adsmartbandapp.screens.PermissionsScreen
import com.icxcu.adsmartbandapp.screens.PersonalInfoNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.mainNavBar.MainNavigationBarRoot
import com.icxcu.adsmartbandapp.screens.mainNavBar.SWReadingStatus
import com.icxcu.adsmartbandapp.screens.mainNavBar.StatusReadingDbForDashboard
import com.icxcu.adsmartbandapp.screens.navigationGraphs.mainNavigationGraph
import com.icxcu.adsmartbandapp.screens.navigationGraphs.physicalActivityGraph
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.PersonalInfoDataScreenNavStatus
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.PersonalInfoDataScreenRoot
import com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure.BloodPressureScreenNavStatus
import com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure.BloodPressureScreenRoot
import com.icxcu.adsmartbandapp.screens.plotsFields.heartRate.HeartRateScreenNavStatus
import com.icxcu.adsmartbandapp.screens.plotsFields.heartRate.HeartRateScreenRoot
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.PhysicalActivityScreenNavStatus
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.PhysicalActivityScreenRoot
import com.icxcu.adsmartbandapp.screens.progressLoading.CircularProgressLoading
import com.icxcu.adsmartbandapp.screens.viewModelProviders.bloodPressureViewModel
import com.icxcu.adsmartbandapp.screens.viewModelProviders.heartRateViewModel
import com.icxcu.adsmartbandapp.screens.viewModelProviders.mainNavigationViewModel
import com.icxcu.adsmartbandapp.screens.viewModelProviders.personalInfoViewModel
import com.icxcu.adsmartbandapp.screens.viewModelProviders.physicalActivityViewModel
import com.icxcu.adsmartbandapp.ui.theme.ADSmartBandAppTheme
import com.icxcu.adsmartbandapp.viewModels.BloodPressureViewModel
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModel
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.MainNavigationViewModel
import com.icxcu.adsmartbandapp.viewModels.MainNavigationModelFactory
import com.icxcu.adsmartbandapp.viewModels.HeartRateViewModel
import com.icxcu.adsmartbandapp.viewModels.PermissionsViewModel
import com.icxcu.adsmartbandapp.viewModels.PermissionsViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.PersonalInfoViewModel
import com.icxcu.adsmartbandapp.viewModels.PhysicalActivityViewModel
import com.icxcu.adsmartbandapp.viewModels.ScanningBluetoothAdapterStatus
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel
import com.icxcu.adsmartbandapp.viewModels.permissionsRequired
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val REQUEST_ENABLE_BT: Int = 500

class MainActivity : ComponentActivity() {
    private lateinit var mainNavigationViewModel: MainNavigationViewModel
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

                    mainNavigationViewModel = viewModel(
                        it,
                        "DataViewModel",
                        MainNavigationModelFactory(
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
                } else if (splashViewModel.lastAccessedDevice.isNotEmpty() && splashViewModel.lastAccessedDevice[1] == Routes.BluetoothScanner.route) {
                    Log.d("Route", "onCreate: Routes.BluetoothScanner.route")
                    Routes.BluetoothScanner.route
                } else if (splashViewModel.lastAccessedDevice.isNotEmpty() && splashViewModel.lastAccessedDevice[1] == Routes.DataHome.route) {
                    Log.d("Route", "onCreate: Routes.DataHome.route")
                    val bluetoothName = splashViewModel.lastAccessedDevice[2]
                    val bluetoothAddress = splashViewModel.lastAccessedDevice[3]
                    Log.d("Route", "onCreate: $bluetoothName")
                    MainNavigationNestedRoute.MainNavigationMainRoute().route
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
        Log.d("OnRestart", "onRestart: ")
        /*setContent {
            ADSmartBandAppTheme {
                MainContent()
            }
        }*/
    }


    @Composable
    private fun MainContent() {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            val systemUiController = rememberSystemUiController()
            val navMainController = rememberNavController()

            LaunchedEffect(key1 = Unit) {
                systemUiController.setStatusBarColor(
                    color = Color(0xFF233D63),
                    darkIcons = false
                )
            }

            val navLambdaToMainNavigationBar =
                remember(bluetoothScannerViewModel, navMainController) {
                    { name: String, address: String ->
                        if (bluetoothScannerViewModel.scanningBluetoothAdaptersStatus == ScanningBluetoothAdapterStatus.SCANNING_FINISHED_WITH_RESULTS
                            || bluetoothScannerViewModel.scanningBluetoothAdaptersStatus == ScanningBluetoothAdapterStatus.SCANNING_FORCIBLY_STOPPED
                        ) {

                            navMainController.navigate(
                                MainNavigationNestedRoute.MainNavigationMainRoute().route
                            )

                            if (bluetoothScannerViewModel.bluetoothAdaptersList.isEmpty()) {
                                bluetoothScannerViewModel.scanningBluetoothAdaptersStatus =
                                    ScanningBluetoothAdapterStatus.NO_SCANNING_WELCOME_SCREEN
                            } else {
                                bluetoothScannerViewModel.scanningBluetoothAdaptersStatus =
                                    ScanningBluetoothAdapterStatus.NO_SCANNING_WITH_RESULTS
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

            val setFetchingDataFromSWStatusSTOPPED = remember(mainNavigationViewModel) {
                {
                    bluetoothScannerViewModel.stateBluetoothListScreenNavigationStatus =
                        BluetoothListScreenNavigationStatus.IN_PROGRESS_TO_MAIN_NAV_SCREEN
                    //mainNavigationViewModel.smartWatchState.fetchingDataFromSWStatus = SWReadingStatus.STOPPED
                }
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

                composable(
                    route = Routes.CircularProgressLoading.route,
                    enterTransition = { null },
                    exitTransition = { null }
                ) {
                    CircularProgressLoading()
                }

                composable(Routes.Permissions.route) {
                    PermissionsScreen(
                        activity = this@MainActivity,
                        viewModel = permissionsViewModel, navLambda = navLambdaToBlueScannerScreen
                    )
                }

                composable(Routes.BluetoothScanner.route,
                    enterTransition = {
                        when (initialState.destination.route) {
                            Routes.Permissions.route -> EnterTransition.None
                            Routes.DataHome.route -> EnterTransition.None
                            else -> null
                        }
                    },
                    exitTransition = {
                        when (targetState.destination.route) {
                            Routes.Permissions.route -> ExitTransition.None
                            Routes.DataHome.route -> ExitTransition.None
                            else -> null
                        }
                    }) {
                    Log.d("DATAX", "Routes.BluetoothScanner.route: ENTER")

                    when (bluetoothScannerViewModel.stateBluetoothListScreenNavigationStatus) {
                        BluetoothListScreenNavigationStatus.IN_PROGRESS_TO_BLUETOOTH_SCREEN -> {
                            //clearStateSW(mainNavigationViewModel)
                        }

                        BluetoothListScreenNavigationStatus.IN_PROGRESS_TO_MAIN_NAV_SCREEN -> {
                            Log.d("DATAX", "Routes.BluetoothScanner.route: CLEARED")
                        }
                    }

                    BluetoothScanScreen(
                        getLiveBasicBluetoothAdapterList,
                        setLiveBasicBluetoothAdapterList,
                        getScanningBluetoothAdaptersStatus,
                        bluetoothScannerViewModel,
                        bluetoothLEManager,
                        this@MainActivity,
                        splashViewModel,
                        preferenceDataStoreHelper,
                        setFetchingDataFromSWStatusSTOPPED,
                        navLambdaToMainNavigationBar
                    )
                }

                mainNavigationGraph(
                    bluetoothScannerViewModel,
                    splashViewModel,
                    navMainController
                )

                physicalActivityGraph(
                    splashViewModel,
                    navMainController
                )


                navigation(
                    startDestination = BloodPressureNestedRoute.BloodPressureScreen().route,// "physical_activity",
                    route = BloodPressureNestedRoute.BloodPressureMainRoute().route//"PHYSICAL_ACTIVITY"
                ){
                    composable(
                        BloodPressureNestedRoute.BloodPressureScreen().route,
                        enterTransition = {
                            when (initialState.destination.route) {
                                Routes.DataHome.route ->
                                    EnterTransition.None

                                else -> null
                            }
                        },
                        exitTransition = {
                            when (targetState.destination.route) {
                                Routes.DataHome.route -> ExitTransition.None
                                else -> null
                            }
                        }
                    ) {

                        val bluetoothAddress = splashViewModel.lastAccessedDevice[3]

                        val bloodPressureViewModel = it.bloodPressureViewModel<BloodPressureViewModel>(navController = navMainController)

                        val myDateObj = LocalDateTime.now()
                        val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        val todayFormattedDate = myDateObj.format(myFormatObj)

                        when(bloodPressureViewModel.bloodPressureScreenNavStatus){
                            BloodPressureScreenNavStatus.Leaving->{
                                bloodPressureViewModel.bloodPressureScreenNavStatus = BloodPressureScreenNavStatus.Started
                                bloodPressureViewModel.starListeningBloodPressureDB(todayFormattedDate, bluetoothAddress)
                            }
                            else->{

                            }
                        }

                        BloodPressureScreenRoot(
                            bloodPressureViewModel,
                            bluetoothAddress,
                            navMainController
                        )
                    }
                }

                navigation(
                    startDestination = HeartRateNestedRoute.HeartRateScreen().route,
                    route = HeartRateNestedRoute.HeartRateMainRoute().route,
                ){
                    composable(
                        HeartRateNestedRoute.HeartRateScreen().route,
                        enterTransition = {
                            when (initialState.destination.route) {
                                Routes.DataHome.route ->
                                    EnterTransition.None

                                else -> null
                            }
                        },
                        exitTransition = {
                            when (targetState.destination.route) {
                                Routes.DataHome.route -> ExitTransition.None
                                else -> null
                            }
                        }
                    ) {

                        val bluetoothAddress = splashViewModel.lastAccessedDevice[3]

                        val heartRateViewModel = it.heartRateViewModel<HeartRateViewModel>(navController = navMainController)

                        val myDateObj = LocalDateTime.now()
                        val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        val todayFormattedDate = myDateObj.format(myFormatObj)

                        when(heartRateViewModel.heartRateScreenNavStatus){
                            HeartRateScreenNavStatus.Leaving->{
                                heartRateViewModel.heartRateScreenNavStatus = HeartRateScreenNavStatus.Started
                                heartRateViewModel.starListeningHeartRateDB(todayFormattedDate, bluetoothAddress)
                            }
                            else->{

                            }
                        }

                        HeartRateScreenRoot(
                            heartRateViewModel,
                            bluetoothAddress,
                            navMainController
                        )
                    }
                }

                navigation(
                    startDestination = PersonalInfoNestedRoute.PersonalInfoScreen().route,
                    route = PersonalInfoNestedRoute.PersonalInfoMainRoute().route
                ){

                    composable(
                        PersonalInfoNestedRoute.PersonalInfoScreen().route,
                        enterTransition = {
                            when (initialState.destination.route) {
                                Routes.DataHome.route -> EnterTransition.None
                                else -> null
                            }
                        },
                        exitTransition = {
                            when (targetState.destination.route) {
                                Routes.DataHome.route -> ExitTransition.None
                                else -> null
                            }
                        }
                    ) {

                        val bluetoothAddress = splashViewModel.lastAccessedDevice[3]

                        val personalInfoViewModel = it.personalInfoViewModel<PersonalInfoViewModel>(navController = navMainController)

                        when(personalInfoViewModel.personalInfoDataScreenNavStatus){
                            PersonalInfoDataScreenNavStatus.Leaving->{
                                personalInfoViewModel.personalInfoDataScreenNavStatus = PersonalInfoDataScreenNavStatus.Started
                                personalInfoViewModel.starListeningPersonalInfoDB(bluetoothAddress)
                            }
                            else->{

                            }
                        }

                        PersonalInfoDataScreenRoot(
                            personalInfoViewModel,
                            bluetoothAddress,
                            navMainController)
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