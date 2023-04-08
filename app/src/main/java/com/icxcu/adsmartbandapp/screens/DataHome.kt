package com.icxcu.adsmartbandapp.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bottombardemo.screens.Favorites
import com.icxcu.adsmartbandapp.repositories.Values

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataHome(
    bluetoothName: String,
    bluetoothAddress: String,
    values: Values,
    navMainController: NavHostController,
    navLambda: () -> Unit
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
            Box(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                NavigationHost(navController = navController, dataSteps = values, navMainController)
            }

        },
        bottomBar = { BottomNavigationBar(navController = navController) }

    )


}


@Composable
fun NavigationHost(
    navController: NavHostController,
    dataSteps: Values,
    navMainController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Fields.route,
    ) {
        composable(NavRoutes.Fields.route) {
            ListFields(dataSteps, navMainController)
        }
        composable(NavRoutes.CheckHealth.route) {
            TestingHealthScreen()
        }

        composable(NavRoutes.Settings.route) {
            Favorites()
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
                        modifier = Modifier.scale(1.2f), painter = painterResource(navItem.image),
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
fun DefaultPreview() {
    val stepValue = listOf(
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        141,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        239,
        110,
        1455,
        3177,
        2404,
        246,
        315,
        65,
        25,
        74,
        0,
        0,
        0,
        47,
        77,
        1025,
        1600,
        164,
        252,
        37,
        51,
        79,
        0,
        11,
        0,
        17,
        43,
        311,
        0
    )

    var disValue = listOf(
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.109,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.185,
        0.077,
        1.127,
        2.512,
        1.849,
        0.185,
        0.249,
        0.053,
        0.02,
        0.058,
        0.0,
        0.0,
        0.0,
        0.039,
        0.061,
        0.788,
        1.201,
        0.131,
        0.193,
        0.03,
        0.04,
        0.062,
        0.0,
        0.009,
        0.0,
        0.014,
        0.034,
        0.204,
        0.0
    )

    var caloriesValues = listOf(
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        7.1,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        12.1,
        5.1,
        73.7,
        164.2,
        121.0,
        12.0,
        16.4,
        3.4,
        1.3,
        3.8,
        0.0,
        0.0,
        0.0,
        2.6,
        3.9,
        51.6,
        78.6,
        8.5,
        12.7,
        1.9,
        2.6,
        4.1,
        0.0,
        0.6,
        0.0,
        0.8,
        2.3,
        13.3,
        0.0
    )

    val values = Values(stepValue, disValue, caloriesValues)
    DataHome(
        bluetoothName = "Device Fake",
        bluetoothAddress="000000",
        values = values,
        navMainController= rememberNavController(),
        navLambda={}
    )

}