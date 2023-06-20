package com.icxcu.adsmartbandapp.screens.progressLoading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.screens.MainNavigationNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes
import kotlinx.coroutines.delay

@Composable
fun CircularProgressLoading(navController: NavHostController, askPermissions: ArrayList<String>) {

    Box(
        modifier = Modifier
            .background(Color(0xff1d2a35))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){

        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            color = Color(0xFFDCE775),
            strokeWidth = 20.dp
        )

        LaunchedEffect(key1 = true, ){
            delay(500)
            if (askPermissions.isNotEmpty()) {
                navController.navigate(Routes.Permissions.route)
            }else{
                navController.navigate(MainNavigationNestedRoute.MainNavigationMainRoute().route)
            }
        }

    }


}