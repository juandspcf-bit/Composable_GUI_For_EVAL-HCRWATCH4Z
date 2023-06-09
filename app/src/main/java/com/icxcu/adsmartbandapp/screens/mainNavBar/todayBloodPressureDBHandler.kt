package com.icxcu.adsmartbandapp.screens.mainNavBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.doubleFieldUpdateOrInsert
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun TodayBloodPressureDBHandler(
    dataViewModel: DataViewModel
) {

    val getSmartWatchState = remember(dataViewModel) {
        {
            dataViewModel.smartWatchState
        }
    }

    val getTodayPhysicalActivityData = remember(dataViewModel) {
        {
            dataViewModel.todayHealthsDataState
        }
    }

    //Data Sources
/*    val todayBloodPressureResultsFromDB by getTodayPhysicalActivityData().todayBloodPressureResultsFromDB.observeAsState(
        MutableList(0) { BloodPressure(bloodPressureId=-1,macAddress="", dateData="", data="") }.toList()
    )*/

    val todayBloodPressureResultsFromDB = dataViewModel.todayStateBloodPressureData.value

    val todayDateValuesReadFromSW = remember(dataViewModel) {
        {
            getSmartWatchState().todayDateValuesReadFromSW
        }
    }

    //Data Sources**

    val setTodaySystolicListReadFromDB: (List<Double>) -> Unit = {
        getTodayPhysicalActivityData().todaySystolicList = it
    }

    getTodayPhysicalActivityData().todaySystolicList = if (todayBloodPressureResultsFromDB.isEmpty().not()) {
        val filter = todayBloodPressureResultsFromDB.filter { it.typesTable == TypesTable.SYSTOLIC }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsTodaySystolicListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getTodayPhysicalActivityData().isTodaySystolicListAlreadyInsertedInDB = it
    }

    val setIsTodaySystolicListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getTodayPhysicalActivityData().isTodaySystolicListInDBAlreadyUpdated = it
    }

    doubleFieldUpdateOrInsert(
        valuesReadFromSW = todayDateValuesReadFromSW().systolicList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = getTodayPhysicalActivityData().todaySystolicList,
        setDayFieldListReadFromDB = setTodaySystolicListReadFromDB,
        dayFromTableData = todayBloodPressureResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getTodayPhysicalActivityData().isTodaySystolicListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getTodayPhysicalActivityData().isTodaySystolicListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodaySystolicListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodaySystolicListInDBAlreadyUpdated,
        TypesTable.SYSTOLIC,
        dataViewModel.todayFormattedDate
    )


    val setTodayDiastolicListReadFromDB: (List<Double>) -> Unit = {
        getTodayPhysicalActivityData().todayDiastolicList = it
    }

    getTodayPhysicalActivityData().todayDiastolicList = if (todayBloodPressureResultsFromDB.isEmpty().not()) {
        val filter = todayBloodPressureResultsFromDB.filter { it.typesTable == TypesTable.DIASTOLIC }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsTodayDiastolicListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getTodayPhysicalActivityData().isTodayDiastolicListAlreadyInsertedInDB = it
    }

    val setIsTodayDiastolicListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getTodayPhysicalActivityData().isTodayDiastolicListInDBAlreadyUpdated = it
    }

    doubleFieldUpdateOrInsert(
        valuesReadFromSW = todayDateValuesReadFromSW().diastolicList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = getTodayPhysicalActivityData().todayDiastolicList,
        setDayFieldListReadFromDB = setTodayDiastolicListReadFromDB,
        dayFromTableData = todayBloodPressureResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getTodayPhysicalActivityData().isTodayDiastolicListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getTodayPhysicalActivityData().isTodayDiastolicListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayDiastolicListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayDiastolicListInDBAlreadyUpdated,
        TypesTable.DIASTOLIC,
        dataViewModel.todayFormattedDate
    )

}