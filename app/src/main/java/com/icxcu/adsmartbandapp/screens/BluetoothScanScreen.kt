package com.icxcu.adsmartbandapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.bluetooth.BluetoothManager
import com.icxcu.adsmartbandapp.data.BasicBluetoothAdapter
import com.icxcu.adsmartbandapp.viewModels.MainViewModel

@Composable
fun BluetoothScanScreen(mainViewModel: MainViewModel, bluetoothLEManager: BluetoothManager) {
    val basicBluetoothAdapters by mainViewModel.liveBasicBluetoothAdapter.observeAsState(null)
    val statusResultState by mainViewModel.liveStatusResults.observeAsState(-1)

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (rowBar, divider, listData) = createRefs()

        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .constrainAs(rowBar) {
                    top.linkTo(parent.top)
                    linkTo(parent.start, parent.end)
                    height = Dimension.fillToConstraints
                }
                .background(Color(61, 56, 70, 255))) {
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
                        bluetoothLEManager.scanLeDevice(scanLocalBluetooth)
                              //scanLocalBluetooth(context)
                        //mainActivity.scanLeDevice(scanLocalBluetooth)
                    }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(163, 163, 117, 255),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Find Bluetooth")
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

        val list = basicBluetoothAdapters?.toList() ?: listOf()

        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .constrainAs(listData) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(divider.bottom)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
                .background(Color(56, 56, 56, 255))) {

            when (statusResultState) {
                0 -> CircularProgressIndicator(
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color(56, 56, 56, 255)),
                    color = Color.White,
                    strokeWidth = 18.dp
                )

                1 -> ListAlbumData(basicBluetoothAdapter = list)
                -1 ->
                    Text(
                        text = "click find bluetooth",
                        style = MaterialTheme.typography.h3,
                        color = Color.White
                    )
            }

        }

    }

}

@Composable
fun ListAlbumData(
    basicBluetoothAdapter: List<BasicBluetoothAdapter>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(basicBluetoothAdapter) {

            Card(
                modifier.padding(top = 5.dp, bottom = 5.dp),
                backgroundColor = Color(60, 63, 65, 255),
                shape = RoundedCornerShape(size = 26.dp),
                border = BorderStroke(width = 1.dp, color = Color.Green),
                elevation = 4.dp
            ) {
                Text(
                    text = "${it.name}: ${it.address} ",
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.h3, color = Color.White
                )

            }

        }
    }
}