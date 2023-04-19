package com.icxcu.adsmartbandapp.screens.dashBoard


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.repositories.Values
import com.icxcu.adsmartbandapp.viewModels.DataViewModel


@Composable
fun DashBoard(
    bluetoothName: String,
    bluetoothAddress: String,
    values: Values,
    dataViewModel: DataViewModel,
    navMainController: NavHostController,
    navLambda: () -> Unit
) {

    TodayPhysicalActivityDBHandler(dataViewModel = dataViewModel)
    YesterdayPhysicalActivityDBHandler(dataViewModel = dataViewModel)

    DashBoardScaffold(
        bluetoothName,
        bluetoothAddress,
        values,
        navMainController,
        navLambda
    )
}



