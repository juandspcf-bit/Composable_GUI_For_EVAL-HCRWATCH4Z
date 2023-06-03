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
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel

@Composable
fun TodayHeartRateDBHandler(
    dataViewModel: DataViewModel,
    splashViewModel: SplashViewModel
) {

    val getSmartWatchState = remember(dataViewModel) {
        {
            dataViewModel.smartWatchState
        }
    }

    val getTodayPhysicalActivityData = remember(dataViewModel) {
        {
            dataViewModel.todayPhysicalActivityInfoState
        }
    }

    //Data Sources
    val todayHeartRateResultsFromDB by getTodayPhysicalActivityData().todayHeartRateResultsFromDB.observeAsState(
        MutableList(0) { HeartRate(heartRateId=-1,macAddress="", dateData="", data="") }.toList()
    )

    val todayDateValuesReadFromSW = remember(dataViewModel) {
        {
            getSmartWatchState().todayDateValuesReadFromSW
        }
    }

    val setTodayHeartRateListReadFromDB: (List<Double>) -> Unit = {
        getTodayPhysicalActivityData().todayHeartRateListReadFromDB = it
    }


    getTodayPhysicalActivityData().todayHeartRateListReadFromDB = if (todayHeartRateResultsFromDB.isEmpty().not()) {
        val filter = todayHeartRateResultsFromDB.filter { it.typesTable == TypesTable.HEART_RATE }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsTodayHeartRateListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getTodayPhysicalActivityData().isTodayHeartRateListAlreadyInsertedInDB = it
    }

    val setIsTodayHeartRateListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getTodayPhysicalActivityData().isTodayHeartRateListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        valuesReadFromSW = todayDateValuesReadFromSW().heartRateList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = getTodayPhysicalActivityData().todayHeartRateListReadFromDB,
        setDayFieldListReadFromDB = setTodayHeartRateListReadFromDB,
        dayFromTableData = todayHeartRateResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getTodayPhysicalActivityData().isTodayHeartRateListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getTodayPhysicalActivityData().isTodayHeartRateListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayHeartRateListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayHeartRateListInDBAlreadyUpdated,
        TypesTable.HEART_RATE,
        dataViewModel.todayFormattedDate
    )

}