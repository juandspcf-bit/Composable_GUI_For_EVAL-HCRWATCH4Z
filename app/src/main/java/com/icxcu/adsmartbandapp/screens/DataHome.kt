package com.icxcu.adsmartbandapp.screens


import android.widget.Toast
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
import com.icxcu.adsmartbandapp.MainActivity
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataHome(
    bluetoothName: String,
    bluetoothAddress: String,
    mainActivity: MainActivity?,
    dataSteps: List<Int>,
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
                        Toast.makeText(mainActivity, "Back Icon Click", Toast.LENGTH_SHORT)
                            .show()
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
                NavigationHost(navController = navController, dataSteps = dataSteps, navMainController)
            }

        },
        bottomBar = { BottomNavigationBar(navController = navController) }

    )


}


@Composable
fun NavigationHost(
    navController: NavHostController,
    dataSteps: List<Int>,
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

}