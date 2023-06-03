package com.icxcu.adsmartbandapp.screens.mainNavBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.doubleFieldUpdateOrInsert
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.integerFieldUpdateOrInsert
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getIntegerListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel

@Composable
fun YesterdayPhysicalActivityDBHandler(
    dataViewModel: DataViewModel,
    splashViewModel: SplashViewModel
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
    val yesterdayPhysicalActivityResultsFromDB by getYesterdayPhysicalActivityData().yesterdayPhysicalActivityResultsFromDB.observeAsState(
        MutableList(0) { PhysicalActivity(physicalActivityId=-1,macAddress="", dateData="", data="") }.toList()
    )

    val yesterdayDateValuesReadFromSW = remember(dataViewModel) {
        {
            getSmartWatchState().yesterdayDateValuesFromSW
        }
    }

    //Data Sources**

    val setYesterdayStepListReadFromDB: (List<Int>) -> Unit = {
        getYesterdayPhysicalActivityData().yesterdayStepListReadFromDB = it
    }

    getYesterdayPhysicalActivityData().yesterdayStepListReadFromDB = if (yesterdayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = yesterdayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.STEPS }
        getIntegerListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0 }.toList()
    }

    val setIsYesterdayStepsListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getYesterdayPhysicalActivityData().isYesterdayStepsListAlreadyInsertedInDB = it
    }

    val setIsYesterdayStepsListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getYesterdayPhysicalActivityData().isYesterdayStepsListInDBAlreadyUpdated = it
    }

    integerFieldUpdateOrInsert(
        valuesReadFromSW = yesterdayDateValuesReadFromSW().stepList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = getYesterdayPhysicalActivityData().yesterdayStepListReadFromDB,
        setDayFieldListReadFromDB = setYesterdayStepListReadFromDB,
        dayFromTableData = yesterdayPhysicalActivityResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getYesterdayPhysicalActivityData().isYesterdayStepsListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getYesterdayPhysicalActivityData().isYesterdayStepsListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdayStepsListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdayStepsListInDBAlreadyUpdated,
        typesTableToModify = TypesTable.STEPS,
        dateData = dataViewModel.yesterdayFormattedDate
    )

    val setYesterdayDistanceListReadFromDB: (List<Double>) -> Unit = {
        getYesterdayPhysicalActivityData().yesterdayDistanceListReadFromDB = it
    }

    getYesterdayPhysicalActivityData().yesterdayDistanceListReadFromDB = if (yesterdayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = yesterdayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.DISTANCE }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsYesterdayDistanceListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getYesterdayPhysicalActivityData().isYesterdayDistanceListAlreadyInsertedInDB = it
    }

    val setIsYesterdayDistanceListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getYesterdayPhysicalActivityData().isYesterdayDistanceListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        valuesReadFromSW = yesterdayDateValuesReadFromSW().distanceList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = getYesterdayPhysicalActivityData().yesterdayDistanceListReadFromDB,
        setDayFieldListReadFromDB = setYesterdayDistanceListReadFromDB,
        dayFromTableData = yesterdayPhysicalActivityResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getYesterdayPhysicalActivityData().isYesterdayDistanceListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getYesterdayPhysicalActivityData().isYesterdayDistanceListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdayDistanceListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdayDistanceListInDBAlreadyUpdated,
        TypesTable.DISTANCE,
        dataViewModel.yesterdayFormattedDate
    )


    val setYesterdayCaloriesListReadFromDB: (List<Double>) -> Unit = {
        getYesterdayPhysicalActivityData().yesterdayCaloriesListReadFromDB = it
    }

    getYesterdayPhysicalActivityData().yesterdayCaloriesListReadFromDB = if (yesterdayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = yesterdayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.CALORIES }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsYesterdayCaloriesListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getYesterdayPhysicalActivityData().isYesterdayCaloriesListAlreadyInsertedInDB = it
    }

    val setIsYesterdayCaloriesListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getYesterdayPhysicalActivityData().isYesterdayCaloriesListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        valuesReadFromSW = yesterdayDateValuesReadFromSW().caloriesList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = getYesterdayPhysicalActivityData().yesterdayCaloriesListReadFromDB,
        setDayFieldListReadFromDB = setYesterdayCaloriesListReadFromDB,
        dayFromTableData = yesterdayPhysicalActivityResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getYesterdayPhysicalActivityData().isYesterdayCaloriesListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getYesterdayPhysicalActivityData().isYesterdayCaloriesListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdayCaloriesListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdayCaloriesListInDBAlreadyUpdated,
        TypesTable.CALORIES,
        dataViewModel.yesterdayFormattedDate
    )

}