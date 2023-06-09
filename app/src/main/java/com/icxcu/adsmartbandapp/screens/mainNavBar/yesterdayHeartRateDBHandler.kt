package com.icxcu.adsmartbandapp.screens.mainNavBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.doubleFieldUpdateOrInsert
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun YesterdayHeartRateDBHandler(
    dataViewModel: DataViewModel
) {

    val getSmartWatchState = remember(dataViewModel) {
        {
            dataViewModel.smartWatchState
        }
    }

    val getYesterdayPhysicalActivityData = remember(dataViewModel) {
        {
            dataViewModel.yesterdayHealthsDataState
        }
    }
    //Data Sources
    val yesterdayHeartRateResultsFromDB = dataViewModel.yesterdayStateHeartRateData.value

    val yesterdayDateValuesReadFromSW = remember(dataViewModel) {
        {
            getSmartWatchState().yesterdayDateValuesFromSW
        }
    }

    //Data Sources**

    val setYesterdayHeartRateListReadFromDB: (List<Double>) -> Unit = {
        getYesterdayPhysicalActivityData().yesterdayHeartRateListReadFromDB = it
    }

    getYesterdayPhysicalActivityData().yesterdayHeartRateListReadFromDB = if (yesterdayHeartRateResultsFromDB.isEmpty().not()) {
        val filter = yesterdayHeartRateResultsFromDB.filter { it.typesTable == TypesTable.HEART_RATE }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsYesterdayHeartRateListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getYesterdayPhysicalActivityData().isYesterdayHeartRateListAlreadyInsertedInDB = it
    }

    val setIsYesterdayHeartRateListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getYesterdayPhysicalActivityData().isYesterdayHeartRateListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        valuesReadFromSW = yesterdayDateValuesReadFromSW().heartRateList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = getYesterdayPhysicalActivityData().yesterdayHeartRateListReadFromDB,
        setDayFieldListReadFromDB = setYesterdayHeartRateListReadFromDB,
        dayFromTableData = yesterdayHeartRateResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getYesterdayPhysicalActivityData().isYesterdayHeartRateListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getYesterdayPhysicalActivityData().isYesterdayHeartRateListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdayHeartRateListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdayHeartRateListInDBAlreadyUpdated,
        TypesTable.HEART_RATE,
        dataViewModel.yesterdayFormattedDate
    )

}