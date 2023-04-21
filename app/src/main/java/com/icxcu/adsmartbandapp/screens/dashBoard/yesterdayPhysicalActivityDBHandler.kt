package com.icxcu.adsmartbandapp.screens.dashBoard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.screens.plotsFields.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.screens.plotsFields.getIntegerListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun YesterdayPhysicalActivityDBHandler(
    dataViewModel: DataViewModel,
) {
    //Data Sources
    val yesterdayPhysicalActivityResultsFromDB by dataViewModel.yesterdayPhysicalActivityResultsFromDB.observeAsState(
        MutableList(0) { PhysicalActivity(physicalActivityId=-1,macAddress="", dateData="", data="") }.toList()
    )

    val yesterdayDateValuesReadFromSW = {
        dataViewModel.yesterdayDateValuesFromSW
    }
    //Data Sources**

    val setYesterdayStepListReadFromDB: (List<Int>) -> Unit = {
        dataViewModel.yesterdayStepListReadFromDB = it
    }

    dataViewModel.yesterdayStepListReadFromDB = if (yesterdayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = yesterdayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.STEPS }
        getIntegerListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0 }.toList()
    }

    val setIsYesterdayStepsListAlreadyInsertedInDB: (Boolean) -> Unit = {
        dataViewModel.isYesterdayStepsListAlreadyInsertedInDB = it
    }

    val setIsYesterdayStepsListInDBAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.isYesterdayStepsListInDBAlreadyUpdated = it
    }

    integerFieldUpdateOrInsert(
        valuesReadFromSW = yesterdayDateValuesReadFromSW().stepList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = dataViewModel.yesterdayStepListReadFromDB,
        setDayFieldListReadFromDB = setYesterdayStepListReadFromDB,
        dayFromTableData = yesterdayPhysicalActivityResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = dataViewModel.isYesterdayStepsListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = dataViewModel.isYesterdayStepsListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdayStepsListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdayStepsListInDBAlreadyUpdated,
        typesTableToModify = TypesTable.STEPS,
        dateData = dataViewModel.yesterdayFormattedDate
    )

    val setYesterdayDistanceListReadFromDB: (List<Double>) -> Unit = {
        dataViewModel.yesterdayDistanceListReadFromDB = it
    }

    dataViewModel.yesterdayDistanceListReadFromDB = if (yesterdayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = yesterdayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.DISTANCE }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsYesterdayDistanceListAlreadyInsertedInDB: (Boolean) -> Unit = {
        dataViewModel.isYesterdayDistanceListAlreadyInsertedInDB = it
    }

    val setIsYesterdayDistanceListInDBAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.isYesterdayDistanceListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        valuesReadFromSW = yesterdayDateValuesReadFromSW().distanceList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = dataViewModel.yesterdayDistanceListReadFromDB,
        setDayFieldListReadFromDB = setYesterdayDistanceListReadFromDB,
        dayFromTableData = yesterdayPhysicalActivityResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = dataViewModel.isYesterdayDistanceListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = dataViewModel.isYesterdayDistanceListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdayDistanceListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdayDistanceListInDBAlreadyUpdated,
        TypesTable.DISTANCE,
        dataViewModel.yesterdayFormattedDate
    )


    val setYesterdayCaloriesListReadFromDB: (List<Double>) -> Unit = {
        dataViewModel.yesterdayCaloriesListReadFromDB = it
    }

    dataViewModel.yesterdayCaloriesListReadFromDB = if (yesterdayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = yesterdayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.CALORIES }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsYesterdayCaloriesListAlreadyInsertedInDB: (Boolean) -> Unit = {
        dataViewModel.isYesterdayCaloriesListAlreadyInsertedInDB = it
    }

    val setIsYesterdayCaloriesListInDBAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.isYesterdayCaloriesListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        valuesReadFromSW = yesterdayDateValuesReadFromSW().caloriesList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = dataViewModel.yesterdayCaloriesListReadFromDB,
        setDayFieldListReadFromDB = setYesterdayCaloriesListReadFromDB,
        dayFromTableData = yesterdayPhysicalActivityResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = dataViewModel.isYesterdayCaloriesListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = dataViewModel.isYesterdayCaloriesListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsYesterdayCaloriesListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsYesterdayCaloriesListInDBAlreadyUpdated,
        TypesTable.CALORIES,
        dataViewModel.yesterdayFormattedDate
    )

}