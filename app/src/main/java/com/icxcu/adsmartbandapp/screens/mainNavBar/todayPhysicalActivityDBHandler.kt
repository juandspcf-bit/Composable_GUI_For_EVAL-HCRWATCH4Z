package com.icxcu.adsmartbandapp.screens.mainNavBar

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.icxcu.adsmartbandapp.data.TypesTable
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

    val todayPhysicalActivityResultsFromDB = dataViewModel.todayStatePhysicalActivityData.value

    val todayDateValuesReadFromSW = remember(dataViewModel) {
        {
            dataViewModel.smartWatchState.todayDateValuesReadFromSW
        }
    }
    //Data Sources**


    val setTodayStepListReadFromDB= remember(dataViewModel){
        { newData: List<Int> ->
            dataViewModel.todayHealthsDataState.todayStepList = newData
        }
    }


    dataViewModel.todayHealthsDataState.todayStepList = if (todayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = todayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.STEPS }
        if(filter.isNotEmpty()){
            getIntegerListFromStringMap(filter[0].data)
        }else{
            MutableList(48) { 0 }.toList()
        }
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
        fieldListReadFromDB = getTodayPhysicalActivityData().todayStepList,
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
            getTodayPhysicalActivityData().todayDistanceList = newData
        }
    }


    dataViewModel.todayHealthsDataState.todayDistanceList = if (todayPhysicalActivityResultsFromDB.isEmpty().not()) {
        Log.d("MY-DATA", "TodayPhysicalActivityDBHandler: $todayPhysicalActivityResultsFromDB")
        val filter = todayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.DISTANCE }
        if(filter.isNotEmpty()){
            getDoubleListFromStringMap(filter[0].data)
        }else{
            MutableList(48) { 0.0 }.toList()
        }

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
        fieldListReadFromDB = getTodayPhysicalActivityData().todayDistanceList,
        setFieldListState = setTodayDistanceListReadFromDB,
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
            getTodayPhysicalActivityData().todayCaloriesList = newData
        }
    }


    getTodayPhysicalActivityData().todayCaloriesList = if (todayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = todayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.CALORIES }
        if(filter.isNotEmpty()){
            getDoubleListFromStringMap(filter[0].data)
        }else{
            MutableList(48) { 0.0 }.toList()
        }
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
        fieldListReadFromDB = getTodayPhysicalActivityData().todayCaloriesList,
        setFieldListState = setTodayCaloriesListReadFromDB,
        dayFromTableData = todayPhysicalActivityResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getTodayPhysicalActivityData().isTodayCaloriesListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getTodayPhysicalActivityData().isTodayCaloriesListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayCaloriesListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayCaloriesListInDBAlreadyUpdated,
        TypesTable.CALORIES,
        dataViewModel.todayFormattedDate
    )

}