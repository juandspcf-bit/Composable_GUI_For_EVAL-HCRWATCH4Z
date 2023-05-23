package com.icxcu.adsmartbandapp.screens.testHealthsScreen


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.repositories.BloodPressureData
import com.icxcu.adsmartbandapp.repositories.myBloodPressureAlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.MyHeartRateAlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.MySpO2AlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.MyTemperatureAlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.TemperatureData
import com.icxcu.adsmartbandapp.screens.testHealthsScreen.alertDialogs.MyBloodPressureAlertDialogContent
import com.icxcu.adsmartbandapp.screens.testHealthsScreen.alertDialogs.MyHeartRateAlertDialogState
import com.icxcu.adsmartbandapp.screens.testHealthsScreen.alertDialogs.MySpO2AlertDialogState
import com.icxcu.adsmartbandapp.screens.testHealthsScreen.alertDialogs.MyTemperatureAlertDialogContent

@Composable
fun TestingHealthScreen(
    getVisibilityProgressbarForFetchingData: () -> Boolean = { false },
    getMyHeartRateAlertDialogDataHandler: () -> MyHeartRateAlertDialogDataHandler,
    getMyHeartRate: () -> Int,
    getMyBloodPressureDialogDataHandler: () -> myBloodPressureAlertDialogDataHandler,
    getRealTimeBloodPressure: () -> BloodPressureData,
    getCircularProgressBloodPressure: () -> Int,
    getMySpO2AlertDialogDataHandler: () -> MySpO2AlertDialogDataHandler,
    getMySpO2: () -> Double,
    getMyTemperatureAlertDialogDataHandler: () -> MyTemperatureAlertDialogDataHandler,
    getRealTimeTemperature: () -> TemperatureData,
    getCircularProgressTemperature: () -> Int,
) {


    var dialogOpen by remember {
        mutableStateOf(false)
    }
    var selectedButton by remember {
        mutableStateOf(-1)
    }

    val myAlertDialogs = mutableListOf<AlertDialogTestHealth>()
    myAlertDialogs.add(
        AlertDialogTestHealth(
            R.drawable.heart_rate,
            {
                BoxImageCircle(
                    enabled = getVisibilityProgressbarForFetchingData().not(),
                    withImage = true,
                    imageResource = R.drawable.heart_rate,
                    setNumberDialog = { indexButton: Int ->
                        selectedButton = indexButton
                    },
                ) { status: Boolean ->
                    dialogOpen = status
                }
            },
            {
                MyHeartRateAlertDialogState(
                    imageResource = R.drawable.heart_rate,
                    getMyHeartRateAlertDialogDataHandler,
                    getMyHeartRate,
                ) { status: Boolean ->
                    dialogOpen = status
                }
            },

            )
    )

    myAlertDialogs.add(
        AlertDialogTestHealth(
            R.drawable.blood_pressure_gauge,
            {
                BoxImageCircle(
                    enabled = getVisibilityProgressbarForFetchingData().not(),
                    withImage = true,
                    imageResource = R.drawable.blood_pressure_gauge,
                    setNumberDialog = { indexButton: Int ->
                        selectedButton = indexButton
                    },
                ) { status: Boolean ->
                    dialogOpen = status
                }
            },
            {
                MyBloodPressureAlertDialogContent(
                    imageResource = R.drawable.blood_pressure_gauge,
                    getMyBloodPressureDialogDataHandler,
                    getRealTimeBloodPressure,
                    getCircularProgressBloodPressure,
                ) { status: Boolean ->
                    dialogOpen = status
                }
            }
        )
    )

    myAlertDialogs.add(
        AlertDialogTestHealth(
            R.drawable.oxygen_saturation,
            {
                BoxImageCircle(
                    enabled = getVisibilityProgressbarForFetchingData().not(),
                    withImage = true,
                    imageResource = R.drawable.oxygen_saturation,
                    setNumberDialog = { indexButton: Int ->
                        selectedButton = indexButton
                    },
                ) { status: Boolean ->
                    dialogOpen = status
                }
            },
            {

                MySpO2AlertDialogState(
                    imageResource = R.drawable.oxygen_saturation,
                    getMySpO2AlertDialogDataHandler = getMySpO2AlertDialogDataHandler,
                    getMySpO2 = getMySpO2,
                ){ status: Boolean ->
                    dialogOpen = status
                }
            }
        )
    )

    myAlertDialogs.add(
        AlertDialogTestHealth(
            R.drawable.thermometer,
            {
                BoxImageCircle(
                    enabled = getVisibilityProgressbarForFetchingData().not(),
                    withImage = true,
                    imageResource = R.drawable.thermometer,
                    setNumberDialog = { indexButton: Int ->
                        selectedButton = indexButton
                    },
                ) { status: Boolean ->
                    dialogOpen = status
                }
            },
            {
                MyTemperatureAlertDialogContent(
                    imageResource = R.drawable.thermometer,
                    getMyTemperatureAlertDialogDataHandler,
                    getRealTimeTemperature,
                    getCircularProgressTemperature,
                ) { status: Boolean ->
                    dialogOpen = status
                }
            }
        )
    )




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff1d2a35)),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = rememberLazyGridState(),
            contentPadding = PaddingValues(3.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            items(myAlertDialogs) {
                it.button()
            }
        }


        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.test),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )


        if (selectedButton == 0 && dialogOpen) {
            myAlertDialogs[0].dialog()
        } else if (selectedButton == 1 && dialogOpen) {
            myAlertDialogs[1].dialog()
        } else if (selectedButton == 2 && dialogOpen) {
            myAlertDialogs[2].dialog()
        } else if (selectedButton == 3 && dialogOpen) {
            myAlertDialogs[3].dialog()
        }

    }


}


@Composable
fun BoxImageCircle(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    withImage: Boolean = false,
    imageResource: Int = R.drawable.ic_launcher_foreground,
    setNumberDialog: (Int) -> Unit,
    setDialogStatus: (Boolean) -> Unit
) {


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Button(
            modifier = modifier
                .size(90.dp),
            onClick = {
                when (imageResource) {
                    R.drawable.heart_rate -> setNumberDialog(0)
                    R.drawable.blood_pressure_gauge -> setNumberDialog(1)
                    R.drawable.oxygen_saturation -> setNumberDialog(2)
                    R.drawable.thermometer -> setNumberDialog(3)
                }
                setDialogStatus(true)
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            border = BorderStroke(2.dp, Color.Green),
            enabled = enabled
        ) {
            if (withImage) {

                Image(
                    modifier = modifier
                        .fillMaxSize(),
                    painter = painterResource(imageResource),
                    contentDescription = null,
                    contentScale = ContentScale.Inside
                )

            }
        }
    }
}

data class AlertDialogTestHealth(
    val image: Int,
    val button: @Composable () -> Unit,
    val dialog: @Composable () -> Unit,
)


@Preview(showBackground = true)
@Composable
fun TestingHealthScreenPreview() {
    /*TestingHealthScreen(
        requestRealTimeHeartRate = {},
        getRealTimeHeartRate = { 20 },
        stopRequestRealTimeHeartRate = {},
        getRealTimeBloodPressure = {
            BloodPressureData(0, 0)
        },
        requestRealTimeBloodPressure = {},
        stopRequestRealTimeBloodPressure = {},
        getCircularProgressBloodPressure = {5},
        getRealTimeTemperature = {
            TemperatureData(0.0, 0.0)
        },
        requestRealTimeTemperature = {},
        stopRequestRealTimeTemperature = {},
        getCircularProgressTemperature = {5}
    )*/
}
