package com.icxcu.adsmartbandapp.screens.testHealthsScreen.alertDialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.icxcu.adsmartbandapp.R

@Composable
fun MyBloodPressureAlertDialogContent(
    imageResource: Int = R.drawable.ic_launcher_foreground,
    stopRequestRealTimeHeartRate: () -> Unit,
    getRealTimeBloodPressure: () -> Map<String, Int>,
    requestRealTimeBloodPressure: () -> Unit,
    stopRequestRealTimeBloodPressure: () -> Unit,
    setDialogStatus: (Boolean) -> Unit
) {
    Dialog(
        onDismissRequest = {
            setDialogStatus(false)
            stopRequestRealTimeHeartRate()
        },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xff0d1721))
                    .padding(all = 16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Check your blood pressure",
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Image(
                    modifier = Modifier
                        .size(70.dp),
                    painter = painterResource(imageResource),
                    contentDescription = null,
                    contentScale = ContentScale.Inside
                )


                Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly){

                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(end = 20.dp)
                    ) {
                        Text(
                            text = "systolic",
                            modifier = Modifier.padding(top = 10.dp, bottom = 2.dp),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "${getRealTimeBloodPressure()["systolic"]} mmHg",
                            modifier = Modifier.padding(bottom = 5.dp),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(start = 20.dp)
                    ) {
                        Text(
                            text = "diastolic",
                            modifier = Modifier.padding(top = 10.dp, bottom = 2.dp),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "${getRealTimeBloodPressure()["diastolic"]} mmHg",
                            modifier = Modifier.padding(bottom = 5.dp),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }




                CircularProgressIndicator(
                    progress = 0.6f,
                    modifier = Modifier
                        .padding(top = 15.dp, bottom = 15.dp)
                        .size(size = 70.dp),
                    color =  Color(0xFFFFD54F),
                    strokeWidth = 8.dp
                )

                FilledIconToggleButtonSample(
                    Modifier.padding(top = 20.dp, bottom = 20.dp),
                    70.dp,
                    requestRealTimeBloodPressure,
                    stopRequestRealTimeBloodPressure,
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFFE997B3),
                        Color.Black
                    ),
                    onClick = {
                        setDialogStatus(false)
                        stopRequestRealTimeBloodPressure()

                    }) {
                    Text(
                        text = "close",
                        color = Color.Black,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MyBloodPressureAlertDialogContentPreview() {
    MyBloodPressureAlertDialogContent(
        imageResource = R.drawable.blood_pressure_gauge,
        stopRequestRealTimeHeartRate = {},
        getRealTimeBloodPressure = {
            mapOf(
                "systolic" to 0,
                "diastolic" to 0
            )
        },
        requestRealTimeBloodPressure = {},
        stopRequestRealTimeBloodPressure = {}

    ) {}
}