package com.icxcu.adsmartbandapp.screens.dashBoard


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    YesterdayBloodPressureDBHandler(dataViewModel = dataViewModel)
    TodayHeartRateDBHandler(dataViewModel = dataViewModel)
    YesterdayHeartRateDBHandler(dataViewModel = dataViewModel)

    val dayDateValuesReadFromSW = {
        dataViewModel.todayDateValuesReadFromSW
    }
    val getVisibility = {
        dataViewModel.progressbarForFetchingDataFromSW
    }

    val heartRate by dataViewModel.getSharedFlowHeartRate().collectAsState(initial = 0)



    val getRealTimeHeartRate={
        heartRate
    }

    val requestRealTimeHeartRate = {
        dataViewModel.requestSmartWatchDataHeartRate()
    }

    val stopRequestRealTimeHeartRate = {
        dataViewModel.stopRequestSmartWatchDataHeartRate()
    }


    DashBoardScaffold(
        bluetoothName,
        bluetoothAddress,
        dayDateValuesReadFromSW,
        getVisibility,
        requestRealTimeHeartRate,
        getRealTimeHeartRate,
        stopRequestRealTimeHeartRate,
        navMainController,
        navLambda,
    )


}



