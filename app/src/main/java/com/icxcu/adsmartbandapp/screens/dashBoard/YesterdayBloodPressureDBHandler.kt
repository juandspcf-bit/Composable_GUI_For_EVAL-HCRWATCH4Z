package com.icxcu.adsmartbandapp.screens.dashBoard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun YesterdayBloodPressureDBHandler(
    dataViewModel: DataViewModel,
) {
    //Data Sources
    val yesterdayBloodPressureResultsFromDB by dataViewModel.yesterdayBloodPressureResultsFromDB.observeAsState(
        MutableList(0) { BloodPressure(bloodPressureId=-1,macAddress="", dateData="", data="") }.toList()
    )

    val yesterdayDateValuesReadFromSW = {
        dataViewModel.yesterdayDateValuesFromSW
    }
    //Data Sources**

    val setYesterdaySystolicListReadFromDB: (List<Double>) -> Unit = {
        dataViewModel.yesterdaySystolicListReadFromDB = it
    }

    dataViewModel.yesterdaySystolicListReadFromDB = if (yesterdayBloodPressureResultsFromDB.isEmpty().not()) {
        val filter = yesterdayBloodPressureResultsFromDB.filter { it.typesTable == TypesTable.SYSTOLIC }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsYesterdaySystolicListAlreadyInsertedInDB: (Boolean) -> Unit = {
        dataViewModel.isYesterdaySystolicListAlreadyInsertedInDB = it
    }

    val setIsYesterdaySystolicListInDBAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.isYesterdaySystolicListInDBAlreadyUpdated = it
    }

    doubleFieldUpdateOrInsert(
        valuesReadFromSW = yesterdayDateValuesReadFromSW().systolicList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = dataViewModel.yesterdaySystolicListReadFromDB,
        setDayFieldListReadFromDB = setYesterdaySystolicListReadFromDB,
        dayFromTableData = yesterdayBloodPressureResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = dataViewModel.isYesterdaySystolicListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = dataViewModel.isYesterdaySystolicListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdaySystolicListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdaySystolicListInDBAlreadyUpdated,
        TypesTable.SYSTOLIC,
        dataViewModel.yesterdayFormattedDate
    )


    val setYesterdayDiastolicListReadFromDB: (List<Double>) -> Unit = {
        dataViewModel.yesterdayDiastolicListReadFromDB = it
    }

    dataViewModel.yesterdayDiastolicListReadFromDB = if (yesterdayBloodPressureResultsFromDB.isEmpty().not()) {
        val filter = yesterdayBloodPressureResultsFromDB.filter { it.typesTable == TypesTable.DIASTOLIC }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsYesterdayDiastolicListAlreadyInsertedInDB: (Boolean) -> Unit = {
        dataViewModel.isYesterdayDiastolicListAlreadyInsertedInDB = it
    }

    val setIsYesterdayDiastolicListInDBAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.isYesterdayDiastolicListInDBAlreadyUpdated = it
    }

    doubleFieldUpdateOrInsert(
        valuesReadFromSW = yesterdayDateValuesReadFromSW().diastolicList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = dataViewModel.yesterdayDiastolicListReadFromDB,
        setDayFieldListReadFromDB = setYesterdayDiastolicListReadFromDB,
        dayFromTableData = yesterdayBloodPressureResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = dataViewModel.isYesterdayDiastolicListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = dataViewModel.isYesterdayDiastolicListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdayDiastolicListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdayDiastolicListInDBAlreadyUpdated,
        TypesTable.DIASTOLIC,
        dataViewModel.yesterdayFormattedDate
    )

}