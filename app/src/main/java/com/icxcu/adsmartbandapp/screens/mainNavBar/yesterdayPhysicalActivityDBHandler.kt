package com.icxcu.adsmartbandapp.screens.mainNavBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.doubleFieldUpdateOrInsert
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.integerFieldUpdateOrInsert
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getIntegerListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun YesterdayPhysicalActivityDBHandler(
    dataViewModel: DataViewModel
) {
    val getYesterdayHealthsDataState = remember(dataViewModel) {
        {
            dataViewModel.yesterdayHealthsDataState
        }
    }

    val yesterdayPhysicalActivityResultsFromDB = dataViewModel.yesterdayStatePhysicalActivityData.value

    val yesterdayDateValuesReadFromSW = remember(dataViewModel) {
        {
            dataViewModel.smartWatchState.yesterdayDateValuesFromSW
        }
    }

    //Data Sources**

    val setYesterdayStepListState: (List<Int>) -> Unit = {
        getYesterdayHealthsDataState().yesterdayStepList = it
    }

    getYesterdayHealthsDataState().yesterdayStepList = if (yesterdayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = yesterdayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.STEPS }
        getIntegerListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0 }.toList()
    }

    val setIsYesterdayStepsListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getYesterdayHealthsDataState().isYesterdayStepsListAlreadyInsertedInDB = it
    }

    val setIsYesterdayStepsListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getYesterdayHealthsDataState().isYesterdayStepsListInDBAlreadyUpdated = it
    }

    integerFieldUpdateOrInsert(
        valuesReadFromSW = yesterdayDateValuesReadFromSW().stepList,
        dataViewModel = dataViewModel,
        fieldListState = getYesterdayHealthsDataState().yesterdayStepList,
        setFieldListState = setYesterdayStepListState,
        dayFromTableData = yesterdayPhysicalActivityResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = getYesterdayHealthsDataState().isYesterdayStepsListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getYesterdayHealthsDataState().isYesterdayStepsListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdayStepsListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdayStepsListInDBAlreadyUpdated,
        typesTableToModify = TypesTable.STEPS,
        dateData = dataViewModel.yesterdayFormattedDate
    )

    val setYesterdayDistanceListState: (List<Double>) -> Unit = {
        getYesterdayHealthsDataState().yesterdayDistanceList = it
    }

    getYesterdayHealthsDataState().yesterdayDistanceList = if (yesterdayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = yesterdayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.DISTANCE }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsYesterdayDistanceListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getYesterdayHealthsDataState().isYesterdayDistanceListAlreadyInsertedInDB = it
    }

    val setIsYesterdayDistanceListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getYesterdayHealthsDataState().isYesterdayDistanceListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        valuesReadFromSW = yesterdayDateValuesReadFromSW().distanceList,
        dayFromTableData = yesterdayPhysicalActivityResultsFromDB,
        fieldListState = getYesterdayHealthsDataState().yesterdayDistanceList,
        dataViewModel = dataViewModel,
        setFieldListState = setYesterdayDistanceListState,
        isDayFieldListAlreadyInsertedInDB = getYesterdayHealthsDataState().isYesterdayDistanceListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getYesterdayHealthsDataState().isYesterdayDistanceListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdayDistanceListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdayDistanceListInDBAlreadyUpdated,
        TypesTable.DISTANCE,
        dataViewModel.yesterdayFormattedDate
    )


    val setYesterdayCaloriesListState: (List<Double>) -> Unit = {
        getYesterdayHealthsDataState().yesterdayCaloriesList = it
    }

    getYesterdayHealthsDataState().yesterdayCaloriesList = if (yesterdayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = yesterdayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.CALORIES }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsYesterdayCaloriesListAlreadyInsertedInDB: (Boolean) -> Unit = {
        getYesterdayHealthsDataState().isYesterdayCaloriesListAlreadyInsertedInDB = it
    }

    val setIsYesterdayCaloriesListInDBAlreadyUpdated: (Boolean) -> Unit = {
        getYesterdayHealthsDataState().isYesterdayCaloriesListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        valuesReadFromSW = yesterdayDateValuesReadFromSW().caloriesList,
        dayFromTableData = yesterdayPhysicalActivityResultsFromDB,
        fieldListState = getYesterdayHealthsDataState().yesterdayCaloriesList,
        dataViewModel = dataViewModel,
        setFieldListState = setYesterdayCaloriesListState,
        isDayFieldListAlreadyInsertedInDB = getYesterdayHealthsDataState().isYesterdayCaloriesListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = getYesterdayHealthsDataState().isYesterdayCaloriesListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdayCaloriesListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdayCaloriesListInDBAlreadyUpdated,
        TypesTable.CALORIES,
        dataViewModel.yesterdayFormattedDate
    )

}