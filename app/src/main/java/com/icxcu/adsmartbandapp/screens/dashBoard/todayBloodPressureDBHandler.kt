package com.icxcu.adsmartbandapp.screens.dashBoard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.screens.plotsFields.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun TodayBloodPressureDBHandler(
    dataViewModel: DataViewModel,
) {
    //Data Sources
    val todayBloodPressureResultsFromDB by dataViewModel.todayBloodPressureResultsFromDB.observeAsState(
        MutableList(0) { BloodPressure(bloodPressureId=-1,macAddress="", dateData="", data="") }.toList()
    )

    val todayDateValuesReadFromSW = {
        dataViewModel.todayDateValuesReadFromSW
    }
    //Data Sources**

    val setTodaySystolicListReadFromDB: (List<Double>) -> Unit = {
        dataViewModel.todaySystolicListReadFromDB = it
    }

    dataViewModel.todaySystolicListReadFromDB = if (todayBloodPressureResultsFromDB.isEmpty().not()) {
        val filter = todayBloodPressureResultsFromDB.filter { it.typesTable == TypesTable.SYSTOLIC }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsTodaySystolicListAlreadyInsertedInDB: (Boolean) -> Unit = {
        dataViewModel.isTodaySystolicListAlreadyInsertedInDB = it
    }

    val setIsTodaySystolicListInDBAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.isTodaySystolicListInDBAlreadyUpdated = it
    }

    doubleFieldUpdateOrInsert(
        valuesReadFromSW = todayDateValuesReadFromSW().systolic,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = dataViewModel.todaySystolicListReadFromDB,
        setDayFieldListReadFromDB = setTodaySystolicListReadFromDB,
        dayFromTableData = todayBloodPressureResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = dataViewModel.isTodaySystolicListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = dataViewModel.isTodaySystolicListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodaySystolicListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodaySystolicListInDBAlreadyUpdated,
        TypesTable.SYSTOLIC,
        dataViewModel.todayFormattedDate
    )


    val setTodayDiastolicListReadFromDB: (List<Double>) -> Unit = {
        dataViewModel.todayDiastolicListReadFromDB = it
    }

    dataViewModel.todayDiastolicListReadFromDB = if (todayBloodPressureResultsFromDB.isEmpty().not()) {
        val filter = todayBloodPressureResultsFromDB.filter { it.typesTable == TypesTable.DIASTOLIC }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsTodayDiastolicListAlreadyInsertedInDB: (Boolean) -> Unit = {
        dataViewModel.isTodayDiastolicListAlreadyInsertedInDB = it
    }

    val setIsTodayDiastolicListInDBAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.isTodayDiastolicListInDBAlreadyUpdated = it
    }

    doubleFieldUpdateOrInsert(
        valuesReadFromSW = todayDateValuesReadFromSW().diastolic,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = dataViewModel.todayDiastolicListReadFromDB,
        setDayFieldListReadFromDB = setTodayDiastolicListReadFromDB,
        dayFromTableData = todayBloodPressureResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = dataViewModel.isTodayDiastolicListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = dataViewModel.isTodayDiastolicListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayDiastolicListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayDiastolicListInDBAlreadyUpdated,
        TypesTable.DIASTOLIC,
        dataViewModel.todayFormattedDate
    )

}