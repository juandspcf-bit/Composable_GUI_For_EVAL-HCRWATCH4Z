package com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getIntegerListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel

@Composable
fun TodayPhysicalActivityDBHandler(
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

    val todayPhysicalActivityResultsFromDB by getTodayPhysicalActivityData()
        .todayPhysicalActivityResultsFromDB.observeAsState(
        MutableList(0) {
            PhysicalActivity(physicalActivityId=-1,macAddress="", dateData="", data="")
        }.toList()
    )

    val todayDateValuesReadFromSW = remember(dataViewModel) {
        {
            getSmartWatchState().todayDateValuesReadFromSW
        }
    }
    //Data Sources**

    val setTodayStepListReadFromDB: (List<Int>) -> Unit = {
        getTodayPhysicalActivityData().todayStepListReadFromDB = it
    }


    getTodayPhysicalActivityData().todayStepListReadFromDB = if (todayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = todayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.STEPS }
        getIntegerListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0 }.toList()
    }


    val setIsTodayStepsListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getTodayPhysicalActivityData().isTodayStepsListAlreadyInsertedInDB = it
    }

    val setIsTodayStepsListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getTodayPhysicalActivityData().isTodayStepsListInDBAlreadyUpdated = it
    }

    integerFieldUpdateOrInsert(
        valuesReadFromSW = todayDateValuesReadFromSW().stepList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = getTodayPhysicalActivityData().todayStepListReadFromDB,
        setDayFieldListReadFromDB = setTodayStepListReadFromDB,
        dayFromTableData = todayPhysicalActivityResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getTodayPhysicalActivityData().isTodayStepsListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getTodayPhysicalActivityData().isTodayStepsListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayStepsListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayStepsListInDBAlreadyUpdated,
        TypesTable.STEPS,
        dateData = dataViewModel.todayFormattedDate
    )

    val setTodayDistanceListReadFromDB: (List<Double>) -> Unit = {
        getTodayPhysicalActivityData().todayDistanceListReadFromDB = it
    }


    getTodayPhysicalActivityData().todayDistanceListReadFromDB = if (todayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = todayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.DISTANCE }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsTodayDistanceListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getTodayPhysicalActivityData().isTodayDistanceListAlreadyInsertedInDB = it
    }

    val setIsTodayDistanceListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getTodayPhysicalActivityData().isTodayDistanceListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        valuesReadFromSW = todayDateValuesReadFromSW().distanceList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = getTodayPhysicalActivityData().todayDistanceListReadFromDB,
        setDayFieldListReadFromDB = setTodayDistanceListReadFromDB,
        dayFromTableData = todayPhysicalActivityResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getTodayPhysicalActivityData().isTodayDistanceListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getTodayPhysicalActivityData().isTodayDistanceListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayDistanceListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayDistanceListInDBAlreadyUpdated,
        TypesTable.DISTANCE,
        dataViewModel.todayFormattedDate
    )


    val setTodayCaloriesListReadFromDB: (List<Double>) -> Unit = {
        getTodayPhysicalActivityData().todayCaloriesListReadFromDB = it
    }


    getTodayPhysicalActivityData().todayCaloriesListReadFromDB = if (todayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = todayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.CALORIES }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsTodayCaloriesListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getTodayPhysicalActivityData().isTodayCaloriesListAlreadyInsertedInDB = it
    }

    val setIsTodayCaloriesListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getTodayPhysicalActivityData().isTodayCaloriesListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        valuesReadFromSW = todayDateValuesReadFromSW().caloriesList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = getTodayPhysicalActivityData().todayCaloriesListReadFromDB,
        setDayFieldListReadFromDB = setTodayCaloriesListReadFromDB,
        dayFromTableData = todayPhysicalActivityResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getTodayPhysicalActivityData().isTodayCaloriesListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getTodayPhysicalActivityData().isTodayCaloriesListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayCaloriesListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayCaloriesListInDBAlreadyUpdated,
        TypesTable.CALORIES,
        dataViewModel.todayFormattedDate
    )

}