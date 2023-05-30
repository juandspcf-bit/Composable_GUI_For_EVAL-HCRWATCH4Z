package com.icxcu.adsmartbandapp

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.icxcu.adsmartbandapp.bluetooth.BluetoothLEManagerImp
import com.icxcu.adsmartbandapp.bluetooth.BluetoothManager
import com.icxcu.adsmartbandapp.data.local.dataPrefrerences.PreferenceDataStoreHelper
import com.icxcu.adsmartbandapp.repositories.Values
import com.icxcu.adsmartbandapp.screens.BNavStatus
import com.icxcu.adsmartbandapp.screens.BluetoothScanScreen
import com.icxcu.adsmartbandapp.screens.PermissionsScreen
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.mainNavBar.DayPhysicalActivityInfoState
import com.icxcu.adsmartbandapp.screens.mainNavBar.MainNavigationBar
import com.icxcu.adsmartbandapp.screens.mainNavBar.SWReadingStatus
import com.icxcu.adsmartbandapp.screens.mainNavBar.SmartWatchState
import com.icxcu.adsmartbandapp.screens.mainNavBar.TodayPhysicalActivityInfoState
import com.icxcu.adsmartbandapp.screens.mainNavBar.YesterdayPhysicalActivityInfoState
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.InvalidAlertDialogState
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.PersonalDataForm
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.PersonalInfoDBHandler
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.PersonalInfoDataState
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.PersonalInfoInitDBHandlerAD
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.UpdateAlertDialogState
import com.icxcu.adsmartbandapp.screens.personalnfoInit.PersonalInfoInit
import com.icxcu.adsmartbandapp.screens.personalnfoInit.PersonalInfoInitDBHandler
import com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure.BloodPressureInfo
import com.icxcu.adsmartbandapp.screens.plotsFields.heartRate.HeartRateInfo
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.PhysicalActivityInfo
import com.icxcu.adsmartbandapp.screens.progressLoading.CircularProgressLoading
import com.icxcu.adsmartbandapp.ui.theme.ADSmartBandAppTheme
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModel
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import com.icxcu.adsmartbandapp.viewModels.DataViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.PermissionsViewModel
import com.icxcu.adsmartbandapp.viewModels.PermissionsViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel
import kotlinx.coroutines.cancel
import java.sql.Wrapper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val REQUEST_ENABLE_BT: Int = 500

class MainActivity : ComponentActivity() {
    private lateinit var dataViewModel: DataViewModel
    private lateinit var bluetoothLEManager: BluetoothManager
    private lateinit var mViewModel: BluetoothScannerViewModel
    private lateinit var pViewModel: PermissionsViewModel
    private val splashViewModel:SplashViewModel by viewModels()
    private lateinit var startDestination:String
    private lateinit var preferenceDataStoreHelper : PreferenceDataStoreHelper


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
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition{
            preferenceDataStoreHelper = PreferenceDataStoreHelper(this)
            splashViewModel.startDelay(preferenceDataStoreHelper)
            splashViewModel.stateFlow.value
        }

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

                    dataViewModel = viewModel(it,
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

                startDestination = if (askPermissions.isNotEmpty()) {
                    Routes.Permissions.route
                } else if (splashViewModel.route.isNotEmpty() && splashViewModel.route[1] == Routes.BluetoothScanner.route) {
                    Log.d("Route", "onCreate: Routes.BluetoothScanner.route")
                    Routes.BluetoothScanner.route
                } else if (splashViewModel.route.isNotEmpty() && splashViewModel.route[1] == Routes.DataHome.route){
                    Log.d("Route", "onCreate: Routes.DataHome.route")
                    Routes.DataHome.route
                }else{
                    Log.d("Route", "onCreate:default")
                    Routes.CircularProgressLoading.route
                }


                bluetoothLEManager = BluetoothLEManagerImp(this@MainActivity, mViewModel)
                MainContent()
            }
        }
    }


    override fun onRestart() {
        super.onRestart()
        Log.d("DATAX", "onRestart: ")
        setContent {
            ADSmartBandAppTheme {
                MainContent()
            }
        }
    }


    @Composable
    private fun MainContent( ){



        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            val navMainController = rememberNavController()

            val navLambdaDataScreen = remember(mViewModel, navMainController) {
                {   name: String, address: String ->
                    if (mViewModel.liveStatusResults == 1 || mViewModel.liveStatusResults == -1) {
                        Log.d("DATAX", "MainContent-1: ${dataViewModel.smartWatchState.todayDateValuesReadFromSW.stepList.sum()}")

                        navMainController.navigate(
                            Routes.DataHomeFromBluetoothScannerScreen
                                .route + "/${name}/${address}"
                        )

                        mViewModel.liveBasicBluetoothAdapter = mutableListOf()
                        mViewModel.liveStatusResults = -2
                    }
                }
            }


            val navLambdaToBlueScanScreen = remember(navMainController) {
                {

                    navMainController.navigate(Routes.BluetoothScanner.route){
                        popUpTo(navMainController.graph.id){
                            inclusive=true
                        }
                    }

                }
            }


            val navLambdaBackBluetoothScanner = remember(mViewModel, navMainController) {
                {
                    navMainController.popBackStack()
                    mViewModel.liveBasicBluetoothAdapter = mutableListOf()
                    mViewModel.liveStatusResults = -2
                }
            }

            val navLambdaBackDataHome = remember() {
                {
                    navMainController.popBackStack()
                }
            }

            val getFetchingDataFromSWStatus = {
                dataViewModel.smartWatchState.fetchingDataFromSWStatus
            }

            val setFetchingDataFromSWStatusSTOPPED = {
                Log.d("DATAX", "MainContent setFetchingDataFromSWStatusSTOPPED STOPPED")

                dataViewModel.stateBNavStatus = BNavStatus.IN_PROGRESS
                dataViewModel.smartWatchState.fetchingDataFromSWStatus = SWReadingStatus.STOPPED
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
                        viewModel = pViewModel, navLambda = navLambdaToBlueScanScreen
                    )
                }

                composable(Routes.BluetoothScanner.route) {
                    Log.d("DATAX", "Routes.BluetoothScanner.route: ENTER")

                    if(dataViewModel.stateBNavStatus != BNavStatus.IN_PROGRESS) clearStateSW(dataViewModel, mViewModel)



                    Log.d("DATAX", "Routes.BluetoothScanner.route: CLEARED")


                    BluetoothScanScreen(
                        basicBluetoothAdapters = mViewModel.liveBasicBluetoothAdapter,
                        statusResultState = mViewModel.liveStatusResults,
                        mViewModel.leScanCallback,
                        bluetoothLEManager,
                        this@MainActivity,
                        setFetchingDataFromSWStatusSTOPPED,
                        navLambdaDataScreen
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

                    val bluetoothName = backStackEntry.arguments?.getString("bluetoothName")?:"no name"
                    val bluetoothAddress =
                        backStackEntry.arguments?.getString("bluetoothAddress")?:"no address"

                    splashViewModel.writeDataPreferences(preferenceDataStoreHelper,
                        name = bluetoothName ?: "no name",
                        address = bluetoothAddress ?: "no address",
                    )


                    Wrapper(dataViewModel,
                        getFetchingDataFromSWStatus,
                        bluetoothAddress,
                        bluetoothName,
                        navLambdaBackBluetoothScanner,
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

                    Wrapper(dataViewModel,
                        getFetchingDataFromSWStatus,
                    bluetoothAddress,
                    bluetoothName,
                    navLambdaBackBluetoothScanner,
                        navMainController,
                        splashViewModel
                    )

                }



                composable(Routes.StepsPlots.route){
                    Log.d("DATAX", "MainContentStepsPlots:")
                    val myDateObj = LocalDateTime.now()
                    val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val formattedDate = myDateObj.format(myFormatObj)
                    dataViewModel.getDayPhysicalActivityData(formattedDate,
                        dataViewModel.macAddressDeviceBluetooth)
                    PhysicalActivityInfo(
                        dataViewModel = dataViewModel
                    ){ navLambdaBackDataHome() }
                }

                composable(Routes.BloodPressurePlots.route){
                    val myDateObj = LocalDateTime.now()
                    val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val formattedDate = myDateObj.format(myFormatObj)
                    dataViewModel.getDayBloodPressureData(formattedDate,
                        dataViewModel.macAddressDeviceBluetooth)
                    BloodPressureInfo(dataViewModel=dataViewModel){
                        navLambdaBackDataHome()
                    }
                }

                composable(Routes.HeartRatePlot.route){
                    val myDateObj = LocalDateTime.now()
                    val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val formattedDate = myDateObj.format(myFormatObj)
                    dataViewModel.getDayHeartRateData(formattedDate,
                        dataViewModel.macAddressDeviceBluetooth)
                    HeartRateInfo(dataViewModel=dataViewModel){
                        navLambdaBackDataHome()
                    }
                }

                composable(Routes.PersonalInfoForm.route){
                    PersonalInfoInitDBHandlerAD(
                        dataViewModel
                    )
                    PersonalDataForm(dataViewModel=dataViewModel){
                        navLambdaBackDataHome()
                    }
                }

                composable(Routes.PersonalInfoFormInit.route){
                    dataViewModel.getPersonalInfoData(dataViewModel.macAddressDeviceBluetooth)
                    PersonalInfoInitDBHandler(
                        dataViewModel
                    )
                    PersonalInfoInit(dataViewModel=dataViewModel){
                        navLambdaToBlueScanScreen()
                    }
                }

            }


        }



    }


    private fun clearStateSW(dataViewModel: DataViewModel, mViewModel: BluetoothScannerViewModel) {
        dataViewModel.smartWatchState.progressbarForFetchingDataFromSW = false
        dataViewModel.smartWatchState.fetchingDataFromSWStatus = SWReadingStatus.CLEARED
        //mViewModel.liveBasicBluetoothAdapter = mutableListOf()

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







@Composable
fun Wrapper(dataViewModel: DataViewModel,
            getFetchingDataFromSWStatus: () -> SWReadingStatus,
            bluetoothAddress:String,
            bluetoothName:String,
            navLambdaBackBluetoothScanner: () -> Unit,
            navMainController: NavHostController,
            splashViewModel:SplashViewModel
){
    when (getFetchingDataFromSWStatus()) {
        SWReadingStatus.CLEARED -> {
            Log.d("DATAX", "Routes.DataHome.route: CLEARED")
        }
        SWReadingStatus.STOPPED -> {

            Log.d("DATAX", "Routes.DataHome.route: STOPPED")

            dataViewModel.smartWatchState.fetchingDataFromSWStatus =
                SWReadingStatus.IN_PROGRESS

            dataViewModel.listenDataFromSmartWatch()
            dataViewModel.requestSmartWatchData(
                bluetoothName,
                bluetoothAddress
            )

            dataViewModel.macAddressDeviceBluetooth =
                bluetoothAddress
            dataViewModel.nameDeviceBluetooth = bluetoothName

            dataViewModel.getTodayPhysicalActivityData(dataViewModel.macAddressDeviceBluetooth)
            dataViewModel.getYesterdayPhysicalActivityData(dataViewModel.macAddressDeviceBluetooth)
            dataViewModel.getTodayBloodPressureData(dataViewModel.macAddressDeviceBluetooth)
            dataViewModel.getYesterdayBloodPressureData(dataViewModel.macAddressDeviceBluetooth)
            dataViewModel.getTodayHeartRateData(dataViewModel.macAddressDeviceBluetooth)
            dataViewModel.getYesterdayHeartRateData(dataViewModel.macAddressDeviceBluetooth)
            dataViewModel.getPersonalInfoData(dataViewModel.macAddressDeviceBluetooth)
            dataViewModel.statusStartedReadingDataLasThreeDaysData = true


        }

        SWReadingStatus.IN_PROGRESS -> {

            Log.d("DATAX", "Routes.DataHome.route: IN_PROGRESS")


            dataViewModel.macAddressDeviceBluetooth =
                bluetoothAddress
            dataViewModel.nameDeviceBluetooth = bluetoothName
        }

        SWReadingStatus.READ -> {

            Log.d("DATAX", "Routes.DataHome.route: READ")


            dataViewModel.macAddressDeviceBluetooth =
                bluetoothAddress
            dataViewModel.nameDeviceBluetooth = bluetoothName
        }
    }

    PersonalInfoDBHandler(
        dataViewModel
    )
    MainNavigationBar(
        bluetoothName = bluetoothName,
        bluetoothAddress = bluetoothAddress,
        dataViewModel = dataViewModel,
        navMainController = navMainController,
        splashViewModel = splashViewModel
    ) { navLambdaBackBluetoothScanner() }

}







@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ADSmartBandAppTheme {

    }
}