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

@Composable
fun TodayPhysicalActivityDBHandler(
    dataViewModel: DataViewModel
) {

    val getTodayPhysicalActivityData = remember(dataViewModel) {
        {
            dataViewModel.todayHealthsDataState
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
            dataViewModel.smartWatchState.todayDateValuesReadFromSW
        }
    }
    //Data Sources**

    val setTodayStepListReadFromDB= remember(dataViewModel){
        { newData: List<Int> ->
            dataViewModel.todayHealthsDataState.todayStepListReadFromDB = newData
        }
    }


    getTodayPhysicalActivityData().todayStepListReadFromDB = if (todayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = todayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.STEPS }
        getIntegerListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0 }.toList()
    }


    val setIsTodayStepsListAlreadyInsertedInDB= remember(dataViewModel){
        { newValue: Boolean ->
            dataViewModel.todayHealthsDataState.isTodayStepsListAlreadyInsertedInDB = newValue
        }
    }

    val setIsTodayStepsListInDBAlreadyUpdated = remember(dataViewModel){
        { newValue: Boolean ->
            dataViewModel.todayHealthsDataState.isTodayStepsListInDBAlreadyUpdated = newValue
        }
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

    val setTodayDistanceListReadFromDB= remember(dataViewModel){
        { newData: List<Double> ->
            getTodayPhysicalActivityData().todayDistanceListReadFromDB = newData
        }
    }


    getTodayPhysicalActivityData().todayDistanceListReadFromDB = if (todayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = todayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.DISTANCE }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsTodayDistanceListAlreadyInsertedInDB = remember(dataViewModel){
        { newValue: Boolean ->
            getTodayPhysicalActivityData().isTodayDistanceListAlreadyInsertedInDB = newValue
        }
    }

    val setIsTodayDistanceListInDBAlreadyUpdated = remember(dataViewModel){
        { newValue: Boolean ->
            getTodayPhysicalActivityData().isTodayDistanceListInDBAlreadyUpdated = newValue
        }
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


    val setTodayCaloriesListReadFromDB = remember(dataViewModel) {
        { newData: List<Double> ->
            getTodayPhysicalActivityData().todayCaloriesListReadFromDB = newData
        }
    }


    getTodayPhysicalActivityData().todayCaloriesListReadFromDB = if (todayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = todayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.CALORIES }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsTodayCaloriesListAlreadyInsertedInDB = remember(dataViewModel){
        { newData: Boolean ->
            getTodayPhysicalActivityData().isTodayCaloriesListAlreadyInsertedInDB = newData
        }
    }

    val setIsTodayCaloriesListInDBAlreadyUpdated = remember(dataViewModel){
        { newData: Boolean ->
            getTodayPhysicalActivityData().isTodayCaloriesListInDBAlreadyUpdated = newData
        }
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