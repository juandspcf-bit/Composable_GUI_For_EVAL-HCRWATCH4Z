package com.icxcu.adsmartbandapp.screens.testHealthsScreen.alertDialogs

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.repositories.MyHeartRateAlertDialogDataHandler

@Composable
fun MyHeartRateAlertDialogState(
    imageResource: Int = R.drawable.ic_launcher_foreground,
    getMyHeartRateAlertDialogDataHandler: () -> MyHeartRateAlertDialogDataHandler,
    getMyHeartRate: () -> Int,
    setDialogStatus: (Boolean) -> Unit
) {

    //val heartRate by getMyHeartRateAlertDialogDataHandler().getSharedFlowHeartRate().collectAsState(initial = 0)

    Log.d("Executed", "MyHeartRateAlertDialogState: ")

    Dialog(
        onDismissRequest = {
            setDialogStatus(false)
            getMyHeartRateAlertDialogDataHandler().stopRequestSmartWatchDataHeartRate()
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
                    text = "Check your heart rate",
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

                Text(
                    text = "${getMyHeartRate()} BPM",
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )


                FilledIconToggleButtonSampleHeartRate(
                    Modifier.padding(top = 20.dp, bottom = 20.dp),
                    70.dp,
                    getMyHeartRateAlertDialogDataHandler,
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
                        getMyHeartRateAlertDialogDataHandler().stopRequestSmartWatchDataHeartRate()

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


@Composable
fun FilledIconToggleButtonSampleHeartRate(
    modifier: Modifier,
    size: Dp = 100.dp,
    getMyHeartRateAlertDialogDataHandler: () -> MyHeartRateAlertDialogDataHandler,
) {
    var checked by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        FilledIconToggleButton(
            modifier = Modifier.size(size),
            checked = checked,
            onCheckedChange = {
                checked = it
                if (checked) {
                    getMyHeartRateAlertDialogDataHandler().requestSmartWatchDataHeartRate()
                } else {
                    getMyHeartRateAlertDialogDataHandler().stopRequestSmartWatchDataHeartRate()
                }

            },
            colors = IconButtonDefaults.filledIconToggleButtonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.Red,
                checkedContainerColor = Color.DarkGray,
                checkedContentColor = Color(0xFF64B5F6),
            )
        ) {
            if (checked) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_stop_24),
                    contentDescription = "Localized description",
                    modifier = Modifier.fillMaxSize()

                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_play_arrow_24),
                    contentDescription = "Localized description",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

}



@Preview(showBackground = true)
@Composable
fun MyHeartRateAlertDialogStatePreview() {
/*    MyHeartRateAlertDialogState(
        imageResource = R.drawable.heart_rate,
        requestRealTimeHeartRate = {},
        getRealTimeHeartRate = { 20 },
        stopRequestRealTimeHeartRate = {}
    ) {}*/
}