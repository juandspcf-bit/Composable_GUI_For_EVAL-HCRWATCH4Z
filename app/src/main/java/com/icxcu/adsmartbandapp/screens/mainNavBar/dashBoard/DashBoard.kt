package com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.repositories.BloodPressureData
import com.icxcu.adsmartbandapp.repositories.MySpO2AlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.TemperatureData
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
    val getVisibilityProgressbarForFetchingData = {
        dataViewModel.progressbarForFetchingDataFromSW
    }


    val getMyHeartRateAlertDialogDataHandler = {
         dataViewModel.getMyHeartRateAlertDialogDataHandler()
    }

    val heartRate by getMyHeartRateAlertDialogDataHandler().getSharedFlowHeartRate().collectAsState(initial = 0)

    val getMyHeartRate = {
        heartRate
    }


    val getMyBloodPressureDialogDataHandler = {
        dataViewModel.getMyBloodPressureAlertDialogDataHandler()
    }
    val bloodPressure by getMyBloodPressureDialogDataHandler().getSharedFlowBloodPressure().collectAsState(initial = BloodPressureData(0 ,0 ))

    val circularProgressBloodPressure by getMyBloodPressureDialogDataHandler().getStateFlowCircularProgressBloodPressure().collectAsState()

    val getCircularProgressBloodPressure={
        circularProgressBloodPressure
    }

    val getRealTimeBloodPressure={
        bloodPressure
    }



    val getMySpO2AlertDialogDataHandler = {
        dataViewModel.getMySpO2AlertDialogDataHandler()
    }

    val spO2 by getMySpO2AlertDialogDataHandler().getSharedFlowSpO2().collectAsState(initial = 0.0)

    val getMySpO2 = {
        spO2
    }

    val getMyTemperatureAlertDialogDataHandler = {
        dataViewModel.getMyTemperatureAlertDialogDataHandler()
    }

    val temperature by getMyTemperatureAlertDialogDataHandler().getSharedFlowTemperature().collectAsState(initial = TemperatureData(0.0, 0.0))

    val circularProgressTemperature by getMyTemperatureAlertDialogDataHandler().getStateFlowCircularProgressTemperature().collectAsState()

    val getCircularProgressTemperature={
        circularProgressTemperature
    }

    val getRealTimeTemperature={
        temperature
    }








    DashBoardScaffold(
        bluetoothName,
        bluetoothAddress,
        dayDateValuesReadFromSW,
        getVisibilityProgressbarForFetchingData,
        getMyHeartRateAlertDialogDataHandler,
        getMyHeartRate,
        getMyBloodPressureDialogDataHandler,
        getRealTimeBloodPressure,
        getCircularProgressBloodPressure,
        getMySpO2AlertDialogDataHandler,
        getMySpO2,
        getMyTemperatureAlertDialogDataHandler,
        getRealTimeTemperature,
        getCircularProgressTemperature,
        navMainController,
        navLambda,
    )


}



