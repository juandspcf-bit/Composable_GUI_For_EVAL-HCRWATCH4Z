package com.icxcu.adsmartbandapp.screens.dashBoard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.icxcu.adsmartbandapp.screens.Settings
import com.icxcu.adsmartbandapp.data.MockData
import com.icxcu.adsmartbandapp.repositories.Values
import com.icxcu.adsmartbandapp.screens.ListDashBoardCardFields
import com.icxcu.adsmartbandapp.screens.NavBarItems
import com.icxcu.adsmartbandapp.screens.NavRoutes
import com.icxcu.adsmartbandapp.screens.testHealthsScreen.TestingHealthScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScaffold(
    bluetoothName: String = "no name",
    bluetoothAddress: String = "no address",
    dayDateValuesReadFromSW: () -> Values = { MockData.valuesToday },
    getVisibilityProgressbarForFetchingData: () -> Boolean = { false },
    requestRealTimeHeartRate: () -> Unit,
    getRealTimeHeartRate: () -> Int,
    stopRequestRealTimeHeartRate: () -> Unit,
    getRealTimeBloodPressure: () -> Map<String, Int>,
    requestRealTimeBloodPressure: () -> Unit,
    stopRequestRealTimeBloodPressure: () -> Unit,
    navMainController: NavHostController = rememberNavController(),
    navLambda: () -> Unit = {}
) {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "$bluetoothName, $bluetoothAddress ", maxLines = 1,
                        overflow = TextOverflow.Ellipsis, color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navLambda()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xff0d1721),
                ),
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {

                AnimatedVisibility(
                    modifier = Modifier
                        .background(Color(0xFFFFB74D)),
                    visible = getVisibilityProgressbarForFetchingData(),
                    enter = expandVertically(animationSpec = tween(durationMillis = 1000)),
                    exit = shrinkVertically(animationSpec = tween(durationMillis = 1000)),
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp, bottom = 15.dp)
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF443B2E)
                        )
                    }
                }

                NavigationHost(
                    navController = navController,
                    dayDateValuesReadFromSW = dayDateValuesReadFromSW,
                    requestRealTimeHeartRate,
                    getRealTimeHeartRate,
                    stopRequestRealTimeHeartRate,
                    getRealTimeBloodPressure,
                    requestRealTimeBloodPressure,
                    stopRequestRealTimeBloodPressure,
                    navMainController
                )
            }

        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    )

}

@Composable
fun NavigationHost(
    navController: NavHostController,
    dayDateValuesReadFromSW: () -> Values,
    requestRealTimeHeartRate: () -> Unit,
    getRealTimeHeartRate: () -> Int,
    stopRequestRealTimeHeartRate: () -> Unit,
    getRealTimeBloodPressure: () -> Map<String, Int>,
    requestRealTimeBloodPressure: () -> Unit,
    stopRequestRealTimeBloodPressure: () -> Unit,
    navMainController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Fields.route,
    ) {
        composable(NavRoutes.Fields.route) {
            ListDashBoardCardFields(
                dayDateValuesReadFromSW,
                navMainController
            )
        }
        composable(NavRoutes.CheckHealth.route) {
            TestingHealthScreen(
                requestRealTimeHeartRate,
                getRealTimeHeartRate,
                stopRequestRealTimeHeartRate,
                getRealTimeBloodPressure,
                requestRealTimeBloodPressure,
                stopRequestRealTimeBloodPressure,
            )
        }

        composable(NavRoutes.Settings.route) {
            Settings(navMainController)
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {

    NavigationBar(
        containerColor = Color(0xff0d1721),
        contentColor = Color(0xFFCDDC39)
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        NavBarItems.BarItems.forEach { navItem ->

            NavigationBarItem(

                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },

                icon = {
                    Icon(
                        modifier = Modifier.scale(1.2f),
                        painter = painterResource(navItem.image),
                        contentDescription = navItem.title,
                        tint = Color.White
                    )
                },
                label = {
                    Text(text = navItem.title, color = Color.White)
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Blue)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DashBoardPreview() {
    DashBoardScaffold(
        getVisibilityProgressbarForFetchingData = { true },
        requestRealTimeHeartRate = {},
        getRealTimeHeartRate = { 0 },
        stopRequestRealTimeHeartRate = {},
        getRealTimeBloodPressure = {
            mapOf(
                "systolic" to 0,
                "diastolic" to 0
            )
        },
        requestRealTimeBloodPressure = {},
        stopRequestRealTimeBloodPressure = {},
    ) {

    }

}