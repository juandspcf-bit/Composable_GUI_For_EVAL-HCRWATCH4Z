package com.icxcu.adsmartbandapp.screens.dashBoard


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.viewModels.DataViewModel


@Composable
fun DashBoard(
    bluetoothName: String,
    bluetoothAddress: String,
    dataViewModel: DataViewModel,
    navMainController: NavHostController,
    navLambda: () -> Unit
) {

    TodayPhysicalActivityDBHandler(dataViewModel = dataViewModel)
    YesterdayPhysicalActivityDBHandler(dataViewModel = dataViewModel)
    TodayBloodPressureDBHandler(dataViewModel = dataViewModel)

    val dayDateValuesReadFromSW = {
        dataViewModel.todayDateValuesReadFromSW
    }
    val getVisibility = {
        dataViewModel.progressbarForFetchingDataFromSW
    }
    DashBoardScaffold(
        bluetoothName,
        bluetoothAddress,
        dayDateValuesReadFromSW,
        getVisibility,
        navMainController,
        navLambda
    )


}



