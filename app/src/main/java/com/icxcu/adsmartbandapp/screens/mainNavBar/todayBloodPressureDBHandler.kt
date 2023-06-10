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

    val getTodayHealthsDataState = remember(dataViewModel) {
        {
            dataViewModel.todayHealthsDataState
        }
    }

    //Data Sources
    val todayBloodPressureResultsFromDB = dataViewModel.todayStateBloodPressureDataReadFromDB.value

    val todayDateValuesReadFromSW = remember(dataViewModel) {
        {
            dataViewModel.smartWatchState.todayDateValuesReadFromSW
        }
    }

    val setTodaySystolicListState: (List<Double>) -> Unit = {
        getTodayHealthsDataState().todaySystolicList = it
    }

    getTodayHealthsDataState().todaySystolicList = if (todayBloodPressureResultsFromDB.isEmpty().not()) {
        val filter = todayBloodPressureResultsFromDB.filter { it.typesTable == TypesTable.SYSTOLIC }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsTodaySystolicListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getTodayHealthsDataState().isTodaySystolicListAlreadyInsertedInDB = it
    }

    val setIsTodaySystolicListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getTodayHealthsDataState().isTodaySystolicListInDBAlreadyUpdated = it
    }

    doubleFieldUpdateOrInsert(
        dataFieldFromSW = todayDateValuesReadFromSW().systolicList,
        dataFieldFromDB = todayBloodPressureResultsFromDB,
        fieldListState = getTodayHealthsDataState().todaySystolicList,
        dataViewModel = dataViewModel,
        setFieldListState = setTodaySystolicListState,
        isDayFieldListAlreadyInsertedInDB = getTodayHealthsDataState().isTodaySystolicListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getTodayHealthsDataState().isTodaySystolicListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodaySystolicListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodaySystolicListInDBAlreadyUpdated,
        TypesTable.SYSTOLIC,
        dataViewModel.todayFormattedDate
    )


    val setTodayDiastolicListState: (List<Double>) -> Unit = {
        getTodayHealthsDataState().todayDiastolicList = it
    }

    getTodayHealthsDataState().todayDiastolicList = if (todayBloodPressureResultsFromDB.isEmpty().not()) {
        val filter = todayBloodPressureResultsFromDB.filter { it.typesTable == TypesTable.DIASTOLIC }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsTodayDiastolicListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getTodayHealthsDataState().isTodayDiastolicListAlreadyInsertedInDB = it
    }

    val setIsTodayDiastolicListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getTodayHealthsDataState().isTodayDiastolicListInDBAlreadyUpdated = it
    }

    doubleFieldUpdateOrInsert(
        dataFieldFromSW = todayDateValuesReadFromSW().diastolicList,
        dataFieldFromDB = todayBloodPressureResultsFromDB,
        fieldListState = getTodayHealthsDataState().todayDiastolicList,
        dataViewModel = dataViewModel,
        setFieldListState = setTodayDiastolicListState,
        isDayFieldListAlreadyInsertedInDB = getTodayHealthsDataState().isTodayDiastolicListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getTodayHealthsDataState().isTodayDiastolicListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayDiastolicListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayDiastolicListInDBAlreadyUpdated,
        TypesTable.DIASTOLIC,
        dataViewModel.todayFormattedDate
    )

}