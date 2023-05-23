package com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
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


class TodayPhysicalActivityInfoState(){

    var todayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()
    var todayStepListReadFromDB by mutableStateOf(listOf<Int>())
    var isTodayStepsListAlreadyInsertedInDB by mutableStateOf(false)
    var isTodayStepsListInDBAlreadyUpdated by mutableStateOf(false)

    var todayDistanceListReadFromDB by mutableStateOf(listOf<Double>())
    var isTodayDistanceListAlreadyInsertedInDB by mutableStateOf(false)
    var isTodayDistanceListInDBAlreadyUpdated by mutableStateOf(false)

    var todayCaloriesListReadFromDB by mutableStateOf(listOf<Double>())
    var isTodayCaloriesListAlreadyInsertedInDB by mutableStateOf(false)
    var isTodayCaloriesListInDBAlreadyUpdated by mutableStateOf(false)

    var todayBloodPressureResultsFromDB = MutableLiveData<List<BloodPressure>>()
    var todaySystolicListReadFromDB by mutableStateOf(listOf<Double>())
    var isTodaySystolicListAlreadyInsertedInDB by mutableStateOf(false)
    var isTodaySystolicListInDBAlreadyUpdated by mutableStateOf(false)

    var todayDiastolicListReadFromDB by mutableStateOf(listOf<Double>())
    var isTodayDiastolicListAlreadyInsertedInDB by mutableStateOf(false)
    var isTodayDiastolicListInDBAlreadyUpdated by mutableStateOf(false)


    var todayHeartRateResultsFromDB = MutableLiveData<List<HeartRate>>()
    var todayHeartRateListReadFromDB by mutableStateOf(listOf<Double>())
    var isTodayHeartRateListAlreadyInsertedInDB by mutableStateOf(false)
    var isTodayHeartRateListInDBAlreadyUpdated by mutableStateOf(false)
}

class YesterdayPhysicalActivityInfoState{
    var yesterdayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()
    var yesterdayStepListReadFromDB by mutableStateOf(listOf<Int>())
    var isYesterdayStepsListAlreadyInsertedInDB = false
    var isYesterdayStepsListInDBAlreadyUpdated = false

    var yesterdayDistanceListReadFromDB by mutableStateOf(listOf<Double>())
    var isYesterdayDistanceListAlreadyInsertedInDB = false
    var isYesterdayDistanceListInDBAlreadyUpdated = false

    var yesterdayCaloriesListReadFromDB by mutableStateOf(listOf<Double>())
    var isYesterdayCaloriesListAlreadyInsertedInDB = false
    var isYesterdayCaloriesListInDBAlreadyUpdated = false

    var yesterdayBloodPressureResultsFromDB = MutableLiveData<List<BloodPressure>>()
    var yesterdaySystolicListReadFromDB by mutableStateOf(listOf<Double>())
    var isYesterdaySystolicListAlreadyInsertedInDB = false
    var isYesterdaySystolicListInDBAlreadyUpdated = false

    var yesterdayDiastolicListReadFromDB by mutableStateOf(listOf<Double>())
    var isYesterdayDiastolicListAlreadyInsertedInDB = false
    var isYesterdayDiastolicListInDBAlreadyUpdated = false

    var yesterdayHeartRateResultsFromDB = MutableLiveData<List<HeartRate>>()
    var yesterdayHeartRateListReadFromDB by mutableStateOf(listOf<Double>())
    var isYesterdayHeartRateListAlreadyInsertedInDB = false
    var isYesterdayHeartRateListInDBAlreadyUpdated = false
}

class DayPhysicalActivityInfoState{
    var dayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()
    var dayStepListFromDB by mutableStateOf(listOf<Int>())
    var dayDistanceListFromDB by mutableStateOf(listOf<Double>())
    var dayCaloriesListFromDB by mutableStateOf(listOf<Double>())

    var dayBloodPressureResultsFromDB = MutableLiveData<List<BloodPressure>>()
    var daySystolicListFromDB by mutableStateOf(listOf<Double>())
    var dayDiastolicListFromDB by mutableStateOf(listOf<Double>())

    var dayHeartRateResultsFromDB = MutableLiveData<List<HeartRate>>()
    var dayHeartRateListFromDB by mutableStateOf(listOf<Double>())
}