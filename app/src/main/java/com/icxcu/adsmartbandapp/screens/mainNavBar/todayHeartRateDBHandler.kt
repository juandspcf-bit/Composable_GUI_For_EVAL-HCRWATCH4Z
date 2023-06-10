package com.icxcu.adsmartbandapp.screens.mainNavBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.doubleFieldUpdateOrInsert
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun TodayHeartRateDBHandler(
    dataViewModel: DataViewModel
) {

    val getTodayHealthsDataState = remember(dataViewModel) {
        {
            dataViewModel.todayHealthsDataState
        }
    }

    //Data Sources
    val todayHeartRateResultsFromDB = dataViewModel.todayStateHeartRateDataReadFromDB.value

    val todayDateValuesReadFromSW = remember(dataViewModel) {
        {
            dataViewModel.smartWatchState.todayDateValuesReadFromSW
        }
    }

    val setTodayHeartRateListState: (List<Double>) -> Unit = {
        getTodayHealthsDataState().todayHeartRateList = it
    }


    getTodayHealthsDataState().todayHeartRateList = if (todayHeartRateResultsFromDB.isEmpty().not()) {
        val filter = todayHeartRateResultsFromDB.filter { it.typesTable == TypesTable.HEART_RATE }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsTodayHeartRateListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getTodayHealthsDataState().isTodayHeartRateListAlreadyInsertedInDB = it
    }

    val setIsTodayHeartRateListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getTodayHealthsDataState().isTodayHeartRateListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        dataFieldFromSW = todayDateValuesReadFromSW().heartRateList,
        dataFieldFromDB = todayHeartRateResultsFromDB,
        fieldListState = getTodayHealthsDataState().todayHeartRateList,
        dataViewModel = dataViewModel,
        setFieldListState = setTodayHeartRateListState,
        isDayFieldListAlreadyInsertedInDB = getTodayHealthsDataState().isTodayHeartRateListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getTodayHealthsDataState().isTodayHeartRateListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayHeartRateListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayHeartRateListInDBAlreadyUpdated,
        TypesTable.HEART_RATE,
        dataViewModel.todayFormattedDate
    )

}