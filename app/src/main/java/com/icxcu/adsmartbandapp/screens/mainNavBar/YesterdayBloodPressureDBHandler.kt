package com.icxcu.adsmartbandapp.screens.mainNavBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.doubleFieldUpdateOrInsert
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun YesterdayBloodPressureDBHandler(
    dataViewModel: DataViewModel
) {

    val getYesterdayPhysicalActivityData = remember(dataViewModel) {
        {
            dataViewModel.yesterdayHealthsDataState
        }
    }


    //Data Sources
    val yesterdayBloodPressureResultsFromDB = dataViewModel.yesterdayStateBloodPressureData.value

    val yesterdayDateValuesReadFromSW = remember(dataViewModel) {
        {
            dataViewModel.smartWatchState.yesterdayDateValuesFromSW
        }
    }

    val setYesterdaySystolicListState: (List<Double>) -> Unit = {
        getYesterdayPhysicalActivityData().yesterdaySystolicList = it
    }

    getYesterdayPhysicalActivityData().yesterdaySystolicList = if (yesterdayBloodPressureResultsFromDB.isEmpty().not()) {
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
        fieldListReadFromDB = getYesterdayPhysicalActivityData().yesterdaySystolicList,
        setFieldListState = setYesterdaySystolicListState,
        dayFromTableData = yesterdayBloodPressureResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getYesterdayPhysicalActivityData().isYesterdaySystolicListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getYesterdayPhysicalActivityData().isYesterdaySystolicListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdaySystolicListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdaySystolicListInDBAlreadyUpdated,
        TypesTable.SYSTOLIC,
        dataViewModel.yesterdayFormattedDate
    )


    val setYesterdayDiastolicListReadFromDB: (List<Double>) -> Unit = {
        getYesterdayPhysicalActivityData().yesterdayDiastolicList = it
    }

    getYesterdayPhysicalActivityData().yesterdayDiastolicList = if (yesterdayBloodPressureResultsFromDB.isEmpty().not()) {
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
        fieldListReadFromDB = getYesterdayPhysicalActivityData().yesterdayDiastolicList,
        setFieldListState = setYesterdayDiastolicListReadFromDB,
        dayFromTableData = yesterdayBloodPressureResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getYesterdayPhysicalActivityData().isYesterdayDiastolicListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getYesterdayPhysicalActivityData().isYesterdayDiastolicListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdayDiastolicListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdayDiastolicListInDBAlreadyUpdated,
        TypesTable.DIASTOLIC,
        dataViewModel.yesterdayFormattedDate
    )

}