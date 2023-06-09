package com.icxcu.adsmartbandapp.screens


import android.app.Activity
import android.bluetooth.le.ScanCallback
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.bluetooth.BluetoothManager
import com.icxcu.adsmartbandapp.data.BasicBluetoothAdapter
import com.icxcu.adsmartbandapp.viewModels.ScanningBluetoothAdapterStatus
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BluetoothScanScreen(
    getLiveBasicBluetoothAdapterList: () -> List<BasicBluetoothAdapter>,
    setLiveBasicBluetoothAdapterList: () -> Unit,
    getScanningBluetoothAdaptersStatus: () -> ScanningBluetoothAdapterStatus,
    leScanCallback: ScanCallback,
    bluetoothLEManager: BluetoothManager,
    activity: Activity,
    setFetchingDataFromSWStatusSTOPPED: () -> Unit,
    navigateMainNavBar: (String, String) -> Unit,
) {
    Log.d("DATAX", "BluetoothScanScreen: ENTER")
    var textState by remember {
        mutableStateOf("Swipe  down to scan devices")
    }
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }


    fun refresh2() = refreshScope.launch {
        refreshing = true
        setLiveBasicBluetoothAdapterList()
        val scanLocalBluetooth = bluetoothLEManager.scanLocalBluetooth(activity)
        bluetoothLEManager.scanLeDevice(
            scanLocalBluetooth,
            leScanCallback
        )
    }

    val state = rememberPullRefreshState(refreshing, ::refresh2, refreshingOffset = 100.dp)
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (rowBar, divider, listData) = createRefs()

        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .constrainAs(rowBar) {
                    top.linkTo(parent.top)
                    linkTo(parent.start, parent.end)
                    height = Dimension.fillToConstraints
                }
                .fillMaxWidth()
                .background(Color(0xff0d1721))) {
            Text(
                text = textState,
                style = MaterialTheme.typography.displayMedium,
                color = Color.White, modifier = Modifier
                    .align(Alignment.Center)
                    .padding(30.dp)
            )

        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(divider) {
                    top.linkTo(rowBar.bottom)
                    linkTo(parent.start, parent.end)
                    height = Dimension.fillToConstraints
                },
            thickness = 2.dp,
            color = Color.Black
        )


        Box(contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .constrainAs(listData) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(divider.bottom)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
                .background(Color(0xff1d2a35))
                .pullRefresh(state)) {

            if (getScanningBluetoothAdaptersStatus() ==
                ScanningBluetoothAdapterStatus.NO_SCANNING_WELCOME_SCREEN
                && getLiveBasicBluetoothAdapterList().isEmpty()) {
                ListAlbumDataEmpty()
            } else if (getScanningBluetoothAdaptersStatus() ==
                ScanningBluetoothAdapterStatus.NO_SCANNING_WITH_RESULTS
                && getLiveBasicBluetoothAdapterList().isNotEmpty()) {
                ListAlbumDataEmpty()
                ListAlbumData(
                    basicBluetoothAdapter = getLiveBasicBluetoothAdapterList(),
                    modifier = Modifier
                        .fillMaxSize(),
                    setFetchingDataFromSWStatusSTOPPED,
                    navigateMainNavBar
                )
            } else {
                ListAlbumDataEmpty()
                ListAlbumData(
                    basicBluetoothAdapter = getLiveBasicBluetoothAdapterList(),
                    modifier = Modifier
                        .fillMaxSize(),
                    setFetchingDataFromSWStatusSTOPPED,
                    navigateMainNavBar
                )
            }

            PullRefreshIndicator(
                refreshing,
                state,
                Modifier
                    .align(Alignment.TopCenter)
                    .size(50.dp),
                scale = true
            )

            when (getScanningBluetoothAdaptersStatus()) {
                ScanningBluetoothAdapterStatus.SCANNING -> {
                    textState = "scanning"
                    //accessed when there is a bluetooth scanning
                }

                ScanningBluetoothAdapterStatus.SCANNING_FINISHED_WITH_RESULTS -> {
                    //accessed when there are results from the bluetooth scan
                    refreshing = false
                    textState = "Swipe  down to scan devices"
                }

                ScanningBluetoothAdapterStatus.SCANNING_FORCIBLY_STOPPED -> {
                    //accessed when the bluetooth scanning is running and should be stopped
                    textState = "Swipe  down to scan devices"
                }

                ScanningBluetoothAdapterStatus.NO_SCANNING_WELCOME_SCREEN -> {
                    //accessed when the bluetooth screen is accessed
                    textState = "Swipe  down to scan devices"
                    Icon(
                        painter = painterResource(R.drawable.baseline_bluetooth_24),
                        contentDescription = "frost",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(80.dp),
                        tint = Color.White
                    )
                }
                ScanningBluetoothAdapterStatus.NO_SCANNING_WITH_RESULTS->{

                }


            }
        }
        Log.d("DATAX", "BluetoothScanScreen: OUT")

    }

}

@Composable
fun ListAlbumData(
    basicBluetoothAdapter: List<BasicBluetoothAdapter>,
    modifier: Modifier = Modifier,
    setFetchingDataFromSWStatusSTOPPED: () -> Unit,
    navigateMainNavBar: (String, String) -> Unit
) {
    LazyColumn(modifier = Modifier) {
        items(basicBluetoothAdapter) { item ->

            Card(
                modifier
                    .padding(top = 5.dp, bottom = 5.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = { /* Press Detected */ },
                            onDoubleTap = { /* Double Tap Detected */ },
                            onLongPress = { /* Long Press Detected */ },
                            onTap = {
                                setFetchingDataFromSWStatusSTOPPED()
                                navigateMainNavBar(item.name, item.address)

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
                    style = MaterialTheme.typography.displaySmall, color = Color.White
                )

            }

        }
    }

}


@Composable
fun ListAlbumDataEmpty(
    modifier: Modifier = Modifier,

    ) {
    LazyColumn(modifier = Modifier) {
        items(5) {

            Card(
                modifier
                    .padding(top = 5.dp, bottom = 5.dp),
                backgroundColor = Color.Transparent,
                shape = RoundedCornerShape(size = 0.dp),
                border = BorderStroke(width = 1.dp, color = Color.Transparent),
                elevation = 0.dp
            ) {
                Text(
                    text = "",
                    modifier = Modifier
                        .padding(100.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge, color = Color.White
                )

            }

        }
    }

}

enum class BluetoothListScreenNavigationStatus {
    IN_PROGRESS_TO_MAIN_NAV_SCREEN,
    IN_PROGRESS_TO_BLUETOOTH_SCREEN,
}