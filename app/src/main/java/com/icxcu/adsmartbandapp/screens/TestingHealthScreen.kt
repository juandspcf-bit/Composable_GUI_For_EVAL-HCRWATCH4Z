package com.icxcu.adsmartbandapp.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilledTonalButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.icxcu.adsmartbandapp.R

@Composable
fun TestingHealthScreen() {


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
                MyHeartRateAlertDialog(
                    imageResource = R.drawable.heart_rate,
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
                MyHeartRateAlertDialog(
                    imageResource = R.drawable.blood_pressure_gauge,
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
/*                MyHeartRateAlertDialog(
                    imageResource = R.drawable.blood_pressure_gauge,
                ) { status: Boolean ->
                    dialogOpen = status
                }*/
            }
        )
    )

    myAlertDialogs.add(
        AlertDialogTestHealth(
            R.drawable.thermometer,
            {
                BoxImageCircle(
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
/*                MyHeartRateAlertDialog(
                    imageResource = R.drawable.blood_pressure_gauge,
                ) { status: Boolean ->
                    dialogOpen = status
                }*/
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
        }

    }


}


@Composable
fun BoxImageCircle(
    modifier: Modifier = Modifier,
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
                }
                setDialogStatus(true)
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            border = BorderStroke(2.dp, Color.Green)
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


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MyHeartRateAlertDialog(
    imageResource: Int = R.drawable.ic_launcher_foreground,
    setDialogStatus: (Boolean) -> Unit
) {
    Dialog(
        onDismissRequest = {
            setDialogStatus(false)
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
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )

                Image(
                    modifier = Modifier
                        .size(100.dp),
                    painter = painterResource(imageResource),
                    contentDescription = null,
                    contentScale = ContentScale.Inside
                )

                Text(
                    text = "0 BPM",
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )


                FilledIconToggleButtonSample(Modifier.padding(top = 20.dp, bottom = 20.dp))

                Button(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFFF06292),
                        Color.White
                    )
                    ,
                    onClick = {
                    setDialogStatus(
                        false
                    )
                }) {
                    Text(
                        text = "close",
                        color = Color.White,
                        fontSize =  MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.Bold
                        )
                }
            }
        }
    }
}

@Composable
fun FilledIconToggleButtonSample(modifier: Modifier
) {
    var checked by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        FilledIconToggleButton(
            modifier =Modifier.size(100.dp),
            checked = checked,
            onCheckedChange = {
                checked = it
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


data class AlertDialogTestHealth(
    val image: Int,
    val button: @Composable () -> Unit,
    val dialog: @Composable () -> Unit,
)


@Preview(showBackground = true)
@Composable
fun TestingHealthScreenPreview() {
    TestingHealthScreen()
}
