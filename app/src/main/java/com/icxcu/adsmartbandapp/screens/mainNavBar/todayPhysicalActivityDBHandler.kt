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

    val getTodayHealthsDataState = remember(dataViewModel) {
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


    val setTodayStepListState= remember(dataViewModel){
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
        dataFieldFromSW = todayDateValuesReadFromSW().stepList,
        dataFieldFromDB = todayPhysicalActivityResultsFromDB,
        fieldListState = getTodayHealthsDataState().todayStepList,
        dataViewModel = dataViewModel,
        setFieldListState = setTodayStepListState,
        isDayFieldListAlreadyInsertedInDB = getTodayHealthsDataState().isTodayStepsListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getTodayHealthsDataState().isTodayStepsListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayStepsListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayStepsListInDBAlreadyUpdated,
        TypesTable.STEPS,
        dateData = dataViewModel.todayFormattedDate
    )

    val setTodayDistanceListState= remember(dataViewModel){
        { newData: List<Double> ->
            getTodayHealthsDataState().todayDistanceList = newData
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
            getTodayHealthsDataState().isTodayDistanceListAlreadyInsertedInDB = newValue
        }
    }

    val setIsTodayDistanceListInDBAlreadyUpdated = remember(dataViewModel){
        { newValue: Boolean ->
            getTodayHealthsDataState().isTodayDistanceListInDBAlreadyUpdated = newValue
        }
    }


    doubleFieldUpdateOrInsert(
        dataFieldFromSW = todayDateValuesReadFromSW().distanceList,
        dataFieldFromDB = todayPhysicalActivityResultsFromDB,
        fieldListState = getTodayHealthsDataState().todayDistanceList,
        dataViewModel = dataViewModel,
        setFieldListState = setTodayDistanceListState,
        isDayFieldListAlreadyInsertedInDB = getTodayHealthsDataState().isTodayDistanceListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getTodayHealthsDataState().isTodayDistanceListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayDistanceListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayDistanceListInDBAlreadyUpdated,
        TypesTable.DISTANCE,
        dataViewModel.todayFormattedDate
    )


    val setTodayCaloriesListState = remember(dataViewModel) {
        { newData: List<Double> ->
            getTodayHealthsDataState().todayCaloriesList = newData
        }
    }


    getTodayHealthsDataState().todayCaloriesList = if (todayPhysicalActivityResultsFromDB.isEmpty().not()) {
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
            getTodayHealthsDataState().isTodayCaloriesListAlreadyInsertedInDB = newData
        }
    }

    val setIsTodayCaloriesListInDBAlreadyUpdated = remember(dataViewModel){
        { newData: Boolean ->
            getTodayHealthsDataState().isTodayCaloriesListInDBAlreadyUpdated = newData
        }
    }


    doubleFieldUpdateOrInsert(
        dataFieldFromSW = todayDateValuesReadFromSW().caloriesList,
        dataFieldFromDB = todayPhysicalActivityResultsFromDB,
        fieldListState = getTodayHealthsDataState().todayCaloriesList,
        dataViewModel = dataViewModel,
        setFieldListState = setTodayCaloriesListState,
        isDayFieldListAlreadyInsertedInDB = getTodayHealthsDataState().isTodayCaloriesListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getTodayHealthsDataState().isTodayCaloriesListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayCaloriesListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayCaloriesListInDBAlreadyUpdated,
        TypesTable.CALORIES,
        dataViewModel.todayFormattedDate
    )

}