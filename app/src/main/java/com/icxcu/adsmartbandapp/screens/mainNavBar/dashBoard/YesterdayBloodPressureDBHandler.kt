package com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel

@Composable
fun YesterdayBloodPressureDBHandler(
    dataViewModel: DataViewModel,
    splashViewModel:SplashViewModel
) {

    val getSmartWatchState = remember(dataViewModel) {
        {
            splashViewModel.smartWatchState
        }
    }

    val getYesterdayPhysicalActivityData = remember(dataViewModel) {
        {
            dataViewModel.yesterdayPhysicalActivityInfoState
        }
    }


    //Data Sources
    val yesterdayBloodPressureResultsFromDB by getYesterdayPhysicalActivityData().yesterdayBloodPressureResultsFromDB.observeAsState(
        MutableList(0) { BloodPressure(bloodPressureId=-1,macAddress="", dateData="", data="") }.toList()
    )

    val yesterdayDateValuesReadFromSW = remember(dataViewModel) {
        {
            getSmartWatchState().yesterdayDateValuesFromSW
        }
    }

    //Data Sources**

    val setYesterdaySystolicListReadFromDB: (List<Double>) -> Unit = {
        getYesterdayPhysicalActivityData().yesterdaySystolicListReadFromDB = it
    }

    getYesterdayPhysicalActivityData().yesterdaySystolicListReadFromDB = if (yesterdayBloodPressureResultsFromDB.isEmpty().not()) {
        val filter = yesterdayBloodPressureResultsFromDB.filter { it.typesTable == TypesTable.SYSTOLIC }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsYesterdaySystolicListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getYesterdayPhysicalActivityData().isYesterdaySystolicListAlreadyInsertedInDB = it
    }

    val setIsYesterdaySystolicListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getYesterdayPhysicalActivityData().isYesterdaySystolicListInDBAlreadyUpdated = it
    }

    doubleFieldUpdateOrInsert(
        valuesReadFromSW = yesterdayDateValuesReadFromSW().systolicList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = getYesterdayPhysicalActivityData().yesterdaySystolicListReadFromDB,
        setDayFieldListReadFromDB = setYesterdaySystolicListReadFromDB,
        dayFromTableData = yesterdayBloodPressureResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getYesterdayPhysicalActivityData().isYesterdaySystolicListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getYesterdayPhysicalActivityData().isYesterdaySystolicListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdaySystolicListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdaySystolicListInDBAlreadyUpdated,
        TypesTable.SYSTOLIC,
        dataViewModel.yesterdayFormattedDate
    )


    val setYesterdayDiastolicListReadFromDB: (List<Double>) -> Unit = {
        getYesterdayPhysicalActivityData().yesterdayDiastolicListReadFromDB = it
    }

    getYesterdayPhysicalActivityData().yesterdayDiastolicListReadFromDB = if (yesterdayBloodPressureResultsFromDB.isEmpty().not()) {
        val filter = yesterdayBloodPressureResultsFromDB.filter { it.typesTable == TypesTable.DIASTOLIC }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsYesterdayDiastolicListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getYesterdayPhysicalActivityData().isYesterdayDiastolicListAlreadyInsertedInDB = it
    }

    val setIsYesterdayDiastolicListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getYesterdayPhysicalActivityData().isYesterdayDiastolicListInDBAlreadyUpdated = it
    }

    doubleFieldUpdateOrInsert(
        valuesReadFromSW = yesterdayDateValuesReadFromSW().diastolicList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = getYesterdayPhysicalActivityData().yesterdayDiastolicListReadFromDB,
        setDayFieldListReadFromDB = setYesterdayDiastolicListReadFromDB,
        dayFromTableData = yesterdayBloodPressureResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getYesterdayPhysicalActivityData().isYesterdayDiastolicListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getYesterdayPhysicalActivityData().isYesterdayDiastolicListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdayDiastolicListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdayDiastolicListInDBAlreadyUpdated,
        TypesTable.DIASTOLIC,
        dataViewModel.yesterdayFormattedDate
    )

}