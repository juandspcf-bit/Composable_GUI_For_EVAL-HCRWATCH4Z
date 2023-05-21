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
fun Settings(navMainController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff1d2a35)),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Row (verticalAlignment = Alignment.CenterVertically){

                Box(modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = { navMainController.navigate(Routes.PersonalInfoForm.route) },
                            onDoubleTap = { /* Double Tap Detected */ },
                            onLongPress = { /* Long Press Detected */ },
                            onTap = {  }
                        )
                    }
                    .fillMaxWidth(0.8f)
                    .padding(end = 20.dp,)
                    .clip(shape = RoundedCornerShape(size = 12.dp))
                    .background(color = Color(0xFFE91E63))
                   ) {

                    Text("Personal Information",
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 10.dp, top=10.dp, bottom = 10.dp)
                    )
                }

                Icon(
                    painter = painterResource(R.drawable.baseline_person_24),
                    contentDescription = "Date Range",
                    tint = Color.White,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            navMainController.navigate(Routes.PersonalInfoForm.route)
                        }

                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    Settings(rememberNavController())
}