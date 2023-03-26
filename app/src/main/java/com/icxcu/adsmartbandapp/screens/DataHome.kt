package com.icxcu.adsmartbandapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons

import androidx.compose.material3.*

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text


import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bottombardemo.screens.Contacts
import com.example.bottombardemo.screens.Favorites
import com.example.bottombardemo.screens.Home
import com.icxcu.adsmartbandapp.MainActivity
import com.icxcu.adsmartbandapp.bluetooth.device.DeviceConnection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataHome(bluetoothName: String,
             bluetoothAddress: String,
             mainActivity: MainActivity?,
            deviceConnection: DeviceConnection?,
             navLambda: () -> Unit) {


    val navController = rememberNavController()
    val scrollBehavior =TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text="$bluetoothName, $bluetoothAddress ", maxLines = 1,
                overflow = TextOverflow.Ellipsis,color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(mainActivity, "Back Icon Click", Toast.LENGTH_SHORT)
                            .show()
                        navLambda()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xff1d2a35),
                ),
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            Box(Modifier.padding(padding).fillMaxSize()) {
                NavigationHost(navController = navController)
            }

        },
        bottomBar = { BottomNavigationBar(navController = navController)}

        )


}


@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Fields.route,
    ) {
        composable(NavRoutes.Fields.route) {
            ListFields()
        }
        composable(NavRoutes.CheckHealth.route) {
            Contacts()
        }

        composable(NavRoutes.Settings.route) {
            Favorites()
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {

    NavigationBar  {
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
                    Icon(imageVector = navItem.image,
                        contentDescription = navItem.title)
                },
                label = {
                    Text(text = navItem.title)
                },
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DataHome(
        bluetoothName = "ddd",
        bluetoothAddress = "ddddd",
        mainActivity = null,
        null,
        {}
    )
}