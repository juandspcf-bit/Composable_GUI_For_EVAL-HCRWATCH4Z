package com.icxcu.adsmartbandapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.bluetooth.BluetoothManager
import com.icxcu.adsmartbandapp.data.BasicBluetoothAdapter
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModel

@Composable
fun BluetoothScanScreen(
    basicBluetoothAdapters: List<BasicBluetoothAdapter>,
    statusResultState: Int,
    bluetoothScannerViewModel: BluetoothScannerViewModel,
    bluetoothLEManager: BluetoothManager,
    navLambda: (String, String) -> Unit,
) {

    var textState by remember {
        mutableStateOf("Star Scan")
    }


    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (rowBar, divider, listData) = createRefs()

        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .constrainAs(rowBar) {
                    top.linkTo(parent.top)
                    linkTo(parent.start, parent.end)
                    height = Dimension.fillToConstraints
                }
                .background(Color(0xff0d1721))) {
            Row(
                modifier = Modifier
                    .padding(bottom = 10.dp, top = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        val scanLocalBluetooth = bluetoothLEManager.scanLocalBluetooth()
                        bluetoothLEManager.scanLeDevice(
                            scanLocalBluetooth,
                            bluetoothScannerViewModel.leScanCallback
                        )

                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(163, 163, 117, 255),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = textState)
                }


                Button(
                    onClick = { bluetoothLEManager.enableBluetooth() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(163, 163, 117, 255),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Enable Bluetooth")
                }

            }
        }

        Divider(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(divider) {
                top.linkTo(rowBar.bottom)
                linkTo(parent.start, parent.end)
                height = Dimension.fillToConstraints
            }
            .size(2.dp)
            .background(Color.Black))


        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .constrainAs(listData) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(divider.bottom)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
                .background(Color(0xff1d2a35))) {



            when (statusResultState) {
                0 -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    textState="stop scanning"

                    ListAlbumData(
                        basicBluetoothAdapter = basicBluetoothAdapters,
                        modifier = Modifier.align(
                            Alignment.TopCenter
                        ),
                        navLambda
                    )
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(200.dp)
                            .background(Color.Transparent),
                        color = Color.White,
                        strokeWidth = 18.dp
                    )
                }
                1 -> {
                    textState="start scanning"
                    ListAlbumData(
                    basicBluetoothAdapter = basicBluetoothAdapters,
                    modifier = Modifier.fillMaxSize(),
                    navLambda
                )}
                -1 ->{
                    textState="start scanning"
                    Text(
                        text = "click find bluetooth",
                        style = MaterialTheme.typography.h3,
                        color = Color.White
                    )
                }

            }

        }

    }

}

@Composable
fun ListAlbumData(
    basicBluetoothAdapter: List<BasicBluetoothAdapter>,
    modifier: Modifier = Modifier,
    navigateRoute: (String, String) -> Unit
) {
    Box(modifier = modifier, contentAlignment = Alignment.TopCenter) {
        LazyColumn() {
            items(basicBluetoothAdapter) {item->

                Card(
                    modifier
                        .padding(top = 5.dp, bottom = 5.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = { /* Press Detected */ },
                                onDoubleTap = { /* Double Tap Detected */ },
                                onLongPress = { /* Long Press Detected */ },
                                onTap = {

                                    navigateRoute(item.name, item.address)

                                }
                            )
                        },
                    backgroundColor = Color(60, 63, 65, 255),
                    shape = RoundedCornerShape(size = 26.dp),
                    border = BorderStroke(width = 1.dp, color = Color.Green),
                    elevation = 4.dp
                ) {
                    Text(
                        text = "${item.name}: ${item.address} ",
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.h3, color = Color.White
                    )

                }

            }
        }
    }

}