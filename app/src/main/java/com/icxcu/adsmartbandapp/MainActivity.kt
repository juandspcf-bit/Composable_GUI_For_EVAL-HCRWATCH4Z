package com.icxcu.adsmartbandapp

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.icxcu.adsmartbandapp.data.local.dataPrefrerences.PreferenceDataStoreHelper
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.navigationGraphs.bloodPressureGraph
import com.icxcu.adsmartbandapp.screens.navigationGraphs.bluetoothScannerGraph
import com.icxcu.adsmartbandapp.screens.navigationGraphs.heartRateGraph
import com.icxcu.adsmartbandapp.screens.navigationGraphs.mainNavigationGraph
import com.icxcu.adsmartbandapp.screens.navigationGraphs.personalInfoGraph
import com.icxcu.adsmartbandapp.screens.navigationGraphs.personalInfoInitGraph
import com.icxcu.adsmartbandapp.screens.navigationGraphs.physicalActivityGraph
import com.icxcu.adsmartbandapp.screens.permissionScreen.PermissionsScreenRoot
import com.icxcu.adsmartbandapp.screens.progressLoading.CircularProgressLoading
import com.icxcu.adsmartbandapp.screens.progressLoading.CircularProgressScreenNavStatus
import com.icxcu.adsmartbandapp.screens.progressLoading.PleaseEnableBluetoothComposable
import com.icxcu.adsmartbandapp.ui.theme.ADSmartBandAppTheme
import com.icxcu.adsmartbandapp.viewModels.CircularProgressViewModel
import com.icxcu.adsmartbandapp.viewModels.CircularProgressViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.PermissionsViewModel
import com.icxcu.adsmartbandapp.viewModels.PermissionsViewModelFactory
import com.icxcu.adsmartbandapp.viewModels.SharedViewModel
import com.icxcu.adsmartbandapp.viewModels.permissionsRequired

const val REQUEST_ENABLE_BT: Int = 500

class MainActivity : ComponentActivity() {
    private lateinit var permissionsViewModel: PermissionsViewModel
    private lateinit var circularProgressViewModel: CircularProgressViewModel
    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var startDestination: String
    private lateinit var navMainController: NavHostController
    //private lateinit var preferenceDataStoreHelper: PreferenceDataStoreHelper


    private var askPermissions = arrayListOf<String>()
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            navMainController.navigate(sharedViewModel.enableBluetoothPortNextRoute)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //preferenceDataStoreHelper = PreferenceDataStoreHelper(this)
        sharedViewModel.preferenceDataStoreHelper = PreferenceDataStoreHelper(this)
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            sharedViewModel.startDelay()
            sharedViewModel.stateFlow.value
        }



        setContent {
            ADSmartBandAppTheme {
                val owner = LocalViewModelStoreOwner.current

                owner?.let {


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
            navMainController = rememberNavController()

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
                        sharedViewModel
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

                composable(Routes.EnableBluetoothPortScreen.route,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }) {
                    PleaseEnableBluetoothComposable(
                        startForResult
                    )
                }

                personalInfoInitGraph(
                    navMainController
                )

                bluetoothScannerGraph(
                    sharedViewModel,
                    this@MainActivity,
                    navMainController,
                )

                mainNavigationGraph(
                    sharedViewModel,
                    navMainController
                )

                physicalActivityGraph(
                    sharedViewModel,
                    navMainController
                )

                bloodPressureGraph(
                    sharedViewModel,
                    navMainController
                )

                heartRateGraph(
                    sharedViewModel,
                    navMainController
                )

                personalInfoGraph(
                    navMainController,
                    startForResult
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