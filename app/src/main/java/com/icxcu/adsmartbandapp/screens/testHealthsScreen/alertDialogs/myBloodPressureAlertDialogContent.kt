package com.icxcu.adsmartbandapp.screens.testHealthsScreen.alertDialogs

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.icxcu.adsmartbandapp.repositories.BloodPressureData
import com.icxcu.adsmartbandapp.repositories.MyBloodPressureAlertDialogDataHandler

@Composable
fun MyBloodPressureAlertDialogContent(
    imageResource: Int = R.drawable.ic_launcher_foreground,
    getMyBloodPressureDialogDataHandler: () -> MyBloodPressureAlertDialogDataHandler,
    getRealTimeBloodPressure: () -> BloodPressureData,
    getCircularProgressBloodPressure: () -> Int,
    setDialogStatus: (Boolean) -> Unit
) {
    Log.d("MyProgress", "MyBloodPressureAlertDialogContent: ${getCircularProgressBloodPressure()}")

    Dialog(
        onDismissRequest = {
            setDialogStatus(false)
            getMyBloodPressureDialogDataHandler().stopRequestSmartWatchDataBloodPressure()
        },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp)
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

                        val systolic = if(getCircularProgressBloodPressure()>0){
                            0
                        }else{
                            getRealTimeBloodPressure().systolic
                        }

                        Text(
                            text = "$systolic mmHg",
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
                        val diastolic = if(getCircularProgressBloodPressure()>0){
                            0
                        }else{
                            getRealTimeBloodPressure().diastolic
                        }
                        Text(
                            text = "$diastolic mmHg",
                            modifier = Modifier.padding(bottom = 5.dp),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                if(getCircularProgressBloodPressure()>0){
                    CircularProgressIndicator(
                        progress = getCircularProgressBloodPressure()/10.0f,
                        modifier = Modifier
                            .padding(top = 15.dp, bottom = 15.dp)
                            .size(size = 70.dp),
                        color =  Color(0xFFFFD54F),
                        strokeWidth = 8.dp
                    )
                }

                FilledIconToggleButtonSampleBloodPressure(
                    Modifier.padding(top = 20.dp, bottom = 20.dp),
                    70.dp,
                    getMyBloodPressureDialogDataHandler,
                    getCircularProgressBloodPressure,
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF49A9F5),
                        Color.White
                    ),
                    onClick = {
                        setDialogStatus(false)
                        getMyBloodPressureDialogDataHandler().stopRequestSmartWatchDataBloodPressure()

                    }) {
                    Text(
                        text = "close",
                        color = Color.White,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun FilledIconToggleButtonSampleBloodPressure(
    modifier: Modifier,
    size: Dp = 100.dp,
    getMyBloodPressureDialogDataHandler: () -> MyBloodPressureAlertDialogDataHandler,
    progressValue:()->Int,
) {
    var checked by remember { mutableStateOf(false) }

    if(checked && progressValue()==0){
        checked=!checked
    }

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
                    getMyBloodPressureDialogDataHandler().requestSmartWatchDataBloodPressure()
                } else {
                    getMyBloodPressureDialogDataHandler().stopRequestSmartWatchDataBloodPressure()
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
fun MyBloodPressureAlertDialogContentPreview() {
/*    MyBloodPressureAlertDialogContent(
        imageResource = R.drawable.blood_pressure_gauge,
        getRealTimeBloodPressure = {
            BloodPressureData(0, 0)
        },
        requestRealTimeBloodPressure = {},
        stopRequestRealTimeBloodPressure = {},
        getCircularProgressBloodPressure = {5}

    ) {}*/
}