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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.icxcu.adsmartbandapp.data.local.dataPrefrerences.PreferenceDataStoreHelper
import com.icxcu.adsmartbandapp.screens.BluetoothScannerNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.bluetoothScanner.BluetoothScannerRoot
import com.icxcu.adsmartbandapp.screens.navigationGraphs.bloodPressureGraph
import com.icxcu.adsmartbandapp.screens.navigationGraphs.heartRateGraph
import com.icxcu.adsmartbandapp.screens.navigationGraphs.mainNavigationGraph
import com.icxcu.adsmartbandapp.screens.navigationGraphs.personalInfoGraph
import com.icxcu.adsmartbandapp.screens.navigationGraphs.personalInfoInitGraph
import com.icxcu.adsmartbandapp.screens.navigationGraphs.physicalActivityGraph
import com.icxcu.adsmartbandapp.screens.permissionScreen.PermissionsScreenRoot
import com.icxcu.adsmartbandapp.screens.progressLoading.CircularProgressLoading
import com.icxcu.adsmartbandapp.screens.progressLoading.CircularProgressScreenNavStatus
import com.icxcu.adsmartbandapp.ui.theme.ADSmartBandAppTheme
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModel
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.CircularProgressViewModel
import com.icxcu.adsmartbandapp.viewModels.CircularProgressViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.PermissionsViewModel
import com.icxcu.adsmartbandapp.viewModels.PermissionsViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.SharedViewModel
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel
import com.icxcu.adsmartbandapp.viewModels.permissionsRequired

const val REQUEST_ENABLE_BT: Int = 500

class MainActivity : ComponentActivity() {
    private lateinit var bluetoothScannerViewModel: BluetoothScannerViewModel
    private lateinit var permissionsViewModel: PermissionsViewModel
    private lateinit var circularProgressViewModel: CircularProgressViewModel
    private val splashViewModel: SplashViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
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

                    circularProgressViewModel = viewModel(
                        it,
                        "PermissionsViewModel",
                        CircularProgressViewModelFactory(LocalContext.current.applicationContext
                                as Application)
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

                startDestination = Routes.CircularProgressLoading.route
                MainContent()
            }
        }
    }


    override fun onRestart() {
        super.onRestart()
        Log.d("OnRestart", "onRestart: ")

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

            NavHost(
                navController = navMainController,
                startDestination = startDestination
            ) {


                composable(
                    route = Routes.CircularProgressLoading.route,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }
                ) {


                    when(circularProgressViewModel.circularProgressScreenNavStatus){
                        CircularProgressScreenNavStatus.Leaving->{
                            circularProgressViewModel.circularProgressScreenNavStatus = CircularProgressScreenNavStatus.Started
                            circularProgressViewModel.starListeningPersonalInfoDB()
                        }
                        else->{

                        }
                    }

                    CircularProgressLoading(
                        navMainController,
                        askPermissions,
                        circularProgressViewModel,
                        splashViewModel
                    )
                }

                composable(Routes.Permissions.route,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }) {
                    PermissionsScreenRoot(
                        activity = this@MainActivity,
                        permissionsViewModel = permissionsViewModel,
                        navMainController = navMainController
                    )
                }

                personalInfoInitGraph(
                    navMainController
                )



                composable(
                    BluetoothScannerNestedRoute.BluetoothScannerScreen().route,
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

                    BluetoothScannerRoot(
                        bluetoothScannerViewModel,
                        this@MainActivity,
                        splashViewModel,
                        preferenceDataStoreHelper,
                        navMainController,
                    )
                }

                mainNavigationGraph(
                    bluetoothScannerViewModel,
                    splashViewModel,
                    navMainController
                )

                physicalActivityGraph(
                    bluetoothScannerViewModel,
                    splashViewModel,
                    navMainController
                )

                bloodPressureGraph(
                    bluetoothScannerViewModel,
                    splashViewModel,
                    navMainController
                )

                heartRateGraph(
                    bluetoothScannerViewModel,
                    splashViewModel,
                    navMainController
                )

                personalInfoGraph(
                    navMainController
                )

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