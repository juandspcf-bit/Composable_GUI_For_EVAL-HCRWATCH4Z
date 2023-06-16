package com.icxcu.adsmartbandapp.screens.mainNavBar.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.screens.PersonalInfoNestedRoute
import com.icxcu.adsmartbandapp.screens.PhysicalActivityNestedRoute
import com.icxcu.adsmartbandapp.screens.Routes

@Composable
fun SettingsScreen(
    navMainController: NavController,
    clearState: () -> Unit,
    getVisibilityProgressbarForFetchingData: () -> Boolean = { false },

    ) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff1d2a35)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            RowSettings(
                navigation = {
                    navMainController.navigate(PersonalInfoNestedRoute.PersonalInfoRoute.route) {
                        popUpTo(Routes.DataHome.route)
                    }
                },
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                text = "Personal information",
                iconResource = R.drawable.baseline_person_24
            )

            RowSettings(
                navigation = {
                    navMainController.navigate(Routes.BluetoothScanner.route) {
                        popUpTo(0)
                    }
                },
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                text = "Connect to other device",
                iconResource = R.drawable.baseline_watch_24,
                disableRow = getVisibilityProgressbarForFetchingData(),
                optionalOperations = clearState,
            )

        }
    }
}

@Composable
fun RowSettings(
    navigation: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "",
    iconResource: Int = R.drawable.ic_launcher_foreground,
    disableRow: Boolean = false,
    optionalOperations: () -> Unit = {},
) {
    Row(
        modifier = modifier.alpha(
            if (disableRow.not()) {
                1f
            } else {
                0.5f
            }
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = if (disableRow.not()) {
                Modifier
                    .clickable {
                        optionalOperations()
                        navigation()
                    }
            } else {
                Modifier
            }
                .fillMaxWidth(0.8f)
                .padding(end = 20.dp)
                .clip(shape = RoundedCornerShape(size = 12.dp))
                .background(color = Color(0xFFE91E63))
        ) {

            Text(
                text,
                textAlign = TextAlign.Start,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
            )
        }

        Icon(
            painter = painterResource(iconResource),
            contentDescription = "Date Range",
            tint = Color.White,
            modifier = if (disableRow.not()) {
                Modifier.clickable {
                    optionalOperations()
                    navigation()
            }} else {
                Modifier
            }.size(50.dp)


        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    SettingsScreen(rememberNavController(), {}) { false }
}