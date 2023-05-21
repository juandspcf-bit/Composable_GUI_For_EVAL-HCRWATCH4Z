package com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun YesterdayHeartRateDBHandler(
    dataViewModel: DataViewModel,
) {
    //Data Sources
    val yesterdayHeartRateResultsFromDB by dataViewModel.yesterdayHeartRateResultsFromDB.observeAsState(
        MutableList(0) { HeartRate(heartRateId=-1,macAddress="", dateData="", data="") }.toList()
    )

    val yesterdayDateValuesReadFromSW = {
        dataViewModel.yesterdayDateValuesFromSW
    }
    //Data Sources**

    val setYesterdayHeartRateListReadFromDB: (List<Double>) -> Unit = {
        dataViewModel.yesterdayHeartRateListReadFromDB = it
    }

    dataViewModel.yesterdayHeartRateListReadFromDB = if (yesterdayHeartRateResultsFromDB.isEmpty().not()) {
        val filter = yesterdayHeartRateResultsFromDB.filter { it.typesTable == TypesTable.HEART_RATE }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsYesterdayHeartRateListAlreadyInsertedInDB: (Boolean) -> Unit = {
        dataViewModel.isYesterdayHeartRateListAlreadyInsertedInDB = it
    }

    val setIsYesterdayHeartRateListInDBAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.isYesterdayHeartRateListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        valuesReadFromSW = yesterdayDateValuesReadFromSW().heartRateList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = dataViewModel.yesterdayHeartRateListReadFromDB,
        setDayFieldListReadFromDB = setYesterdayHeartRateListReadFromDB,
        dayFromTableData = yesterdayHeartRateResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = dataViewModel.isYesterdayHeartRateListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = dataViewModel.isYesterdayHeartRateListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdayHeartRateListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdayHeartRateListInDBAlreadyUpdated,
        TypesTable.HEART_RATE,
        dataViewModel.yesterdayFormattedDate
    )

}