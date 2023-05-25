package com.icxcu.adsmartbandapp.screens.mainNavBar.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.screens.Routes

@Composable
fun SettingsScreen(navMainController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff1d2a35)),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
            RowSettings(
                modifier = Modifier.padding(top=10.dp, bottom = 10.dp),
                text = "personal information",
                navMainController = navMainController,
                route = Routes.PersonalInfoForm.route,
                resource = R.drawable.baseline_person_24
            )
            RowSettings(
                modifier = Modifier.padding(top=10.dp, bottom = 10.dp),
                text = "connect to a different device",
                navMainController = navMainController,
                route = Routes.BluetoothScanner.route,
                resource = R.drawable.baseline_watch_24
            )
        }
    }
}

@Composable
fun RowSettings(
    modifier: Modifier,
    text:String,
    navMainController: NavController,
    route: String,
    resource: Int = R.drawable.ic_launcher_foreground
){
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){

        Box(modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { navMainController.navigate(route) },
                    onDoubleTap = { /* Double Tap Detected */ },
                    onLongPress = { /* Long Press Detected */ },
                    onTap = { }
                )
            }
            .fillMaxWidth(0.8f)
            .padding(end = 20.dp,)
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(color = Color(0xFFE91E63))
        ) {

            Text(
                text =text,
                textAlign = TextAlign.Start,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp, top=10.dp, bottom = 10.dp)
            )
        }

        Icon(
            painter = painterResource(resource),
            contentDescription = "Date Range",
            tint = Color.White,
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    navMainController.navigate(route)
                }.fillMaxWidth(1f)

        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    SettingsScreen(rememberNavController())
}