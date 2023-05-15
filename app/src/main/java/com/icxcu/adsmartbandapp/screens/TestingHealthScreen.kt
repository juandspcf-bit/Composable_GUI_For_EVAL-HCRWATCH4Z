package com.icxcu.adsmartbandapp.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.icxcu.adsmartbandapp.R

@Composable
fun TestingHealthScreen() {
    val fields = listOf(
        R.drawable.heart_rate,
        R.drawable.blood_pressure_gauge,
        R.drawable.oxygen_saturation,
        R.drawable.thermometer
    )

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
            columns = GridCells.Adaptive(120.dp),
            state = rememberLazyGridState(),
            contentPadding = PaddingValues(2.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            items(fields) {

                BoxImageCircle(
                    withImage = true,
                    imageResource = it,
                    setNumberDialog = { indexButton: Int ->
                        selectedButton = indexButton
                    },
                ) { status: Boolean ->
                    dialogOpen = status
                }

            }
        }


        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.test),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        when (selectedButton) {
            0 -> myAlertDialogs[0].dialog()
            else -> {

            }
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

    Button(
        modifier = modifier
            .size(130.dp)

            .padding(start = 0.dp, end = 0.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { /* Press Detected */ },
                    onDoubleTap = { /* Double Tap Detected */ },
                    onLongPress = { /* Long Press Detected */ },
                    onTap = {

                    }
                )
            }
            .padding(5.dp),
        onClick = {
            setDialogStatus(true)
        },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
        border = BorderStroke(2.dp, Color.Green)
        //contentAlignment = Alignment.Center
    ) {
        if (withImage) {

            Image(
                modifier = modifier
                    .size(75.dp),
                painter = painterResource(imageResource),
                contentDescription = null,
                contentScale = ContentScale.Inside
            )

        }


    }
}


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
            dismissOnClickOutside = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Column(modifier = Modifier.padding(all = 16.dp)) {
                Image(
                    modifier = Modifier
                        .size(75.dp),
                    painter = painterResource(imageResource),
                    contentDescription = null,
                    contentScale = ContentScale.Inside
                )
                Text(text = "You cannot close me by clicking outside")
            }
        }
    }
}


data class AlertDialogTestHealth(
    val image: Int,
    val dialog: @Composable () -> Unit,
    val button: @Composable () -> Unit,
)


@Preview(showBackground = true)
@Composable
fun TestingHealthScreenPreview() {
    TestingHealthScreen()
}
