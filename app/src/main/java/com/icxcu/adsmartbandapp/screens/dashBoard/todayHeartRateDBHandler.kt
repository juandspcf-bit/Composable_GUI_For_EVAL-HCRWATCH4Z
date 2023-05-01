package com.icxcu.adsmartbandapp.screens.dashBoard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun TodayHeartRateDBHandler(
    dataViewModel: DataViewModel,
) {

    //Data Sources
    val todayHeartRateResultsFromDB by dataViewModel.todayHeartRateResultsFromDB.observeAsState(
        MutableList(0) { HeartRate(heartRateId=-1,macAddress="", dateData="", data="") }.toList()
    )


    val todayDateValuesReadFromSW = {
        dataViewModel.todayDateValuesReadFromSW
    }

    val setTodayHeartRateListReadFromDB: (List<Double>) -> Unit = {
        dataViewModel.todayHeartRateListReadFromDB = it
    }


    dataViewModel.todayHeartRateListReadFromDB = if (todayHeartRateResultsFromDB.isEmpty().not()) {
        val filter = todayHeartRateResultsFromDB.filter { it.typesTable == TypesTable.HEART_RATE }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsTodayHeartRateListAlreadyInsertedInDB: (Boolean) -> Unit = {
        dataViewModel.isTodayHeartRateListAlreadyInsertedInDB = it
    }

    val setIsTodayHeartRateListInDBAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.isTodayHeartRateListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        valuesReadFromSW = todayDateValuesReadFromSW().heartRateList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = dataViewModel.todayHeartRateListReadFromDB,
        setDayFieldListReadFromDB = setTodayHeartRateListReadFromDB,
        dayFromTableData = todayHeartRateResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = dataViewModel.isTodayHeartRateListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = dataViewModel.isTodayHeartRateListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayHeartRateListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayHeartRateListInDBAlreadyUpdated,
        TypesTable.HEART_RATE,
        dataViewModel.todayFormattedDate
    )

}