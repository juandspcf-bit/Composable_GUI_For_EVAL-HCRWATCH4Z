package com.icxcu.adsmartbandapp.screens.mainNavBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.doubleFieldUpdateOrInsert
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun YesterdayHeartRateDBHandler(
    dataViewModel: DataViewModel
) {

    //state sources

    val yesterdayHealthsDataState = remember(dataViewModel) {
        {
            dataViewModel.yesterdayHealthsDataState
        }
    }


    //Data Sources
    val yesterdayHeartRateResultsFromDB = dataViewModel.yesterdayStateHeartRateData.value

    val yesterdayDateValuesReadFromSW = remember(dataViewModel) {
        {
            dataViewModel.smartWatchState.yesterdayDateValuesFromSW
        }
    }


    val setYesterdayHeartRateListState: (List<Double>) -> Unit = {
        yesterdayHealthsDataState().yesterdayHeartRateList = it
    }

    yesterdayHealthsDataState().yesterdayHeartRateList = if (yesterdayHeartRateResultsFromDB.isEmpty().not()) {
        val filter = yesterdayHeartRateResultsFromDB.filter { it.typesTable == TypesTable.HEART_RATE }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsYesterdayHeartRateListAlreadyInsertedInDB: (Boolean) -> Unit = {
        yesterdayHealthsDataState().isYesterdayHeartRateListAlreadyInsertedInDB = it
    }

    val setIsYesterdayHeartRateListInDBAlreadyUpdated: (Boolean) -> Unit = {
        yesterdayHealthsDataState().isYesterdayHeartRateListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        dataFieldFromSW = yesterdayDateValuesReadFromSW().heartRateList,
        dataFieldFromDB = yesterdayHeartRateResultsFromDB,
        fieldListState = yesterdayHealthsDataState().yesterdayHeartRateList,
        dataViewModel = dataViewModel,
        setFieldListState = setYesterdayHeartRateListState,
        isDayFieldListAlreadyInsertedInDB = yesterdayHealthsDataState().isYesterdayHeartRateListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = yesterdayHealthsDataState().isYesterdayHeartRateListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdayHeartRateListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdayHeartRateListInDBAlreadyUpdated,
        TypesTable.HEART_RATE,
        dataViewModel.yesterdayFormattedDate
    )

}