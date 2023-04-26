package com.icxcu.adsmartbandapp.screens.dashBoard

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getIntegerListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun TodayPhysicalActivityDBHandler(
    dataViewModel: DataViewModel,
) {
    Log.d("Recompositions", "TodayPhysicalActivityDBHandler: ")
    //Data Sources
    val todayPhysicalActivityResultsFromDB by dataViewModel.todayPhysicalActivityResultsFromDB.observeAsState(
        MutableList(0) { PhysicalActivity(physicalActivityId=-1,macAddress="", dateData="", data="") }.toList()
    )


    val todayDateValuesReadFromSW = {
        dataViewModel.todayDateValuesReadFromSW
    }
    //Data Sources**

    val setTodayStepListReadFromDB: (List<Int>) -> Unit = {
        dataViewModel.todayStepListReadFromDB = it
    }


    dataViewModel.todayStepListReadFromDB = if (todayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = todayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.STEPS }
        getIntegerListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0 }.toList()
    }


    val setIsTodayStepsListAlreadyInsertedInDB: (Boolean) -> Unit = {
        dataViewModel.isTodayStepsListAlreadyInsertedInDB = it
    }

    val setIsTodayStepsListInDBAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.isTodayStepsListInDBAlreadyUpdated = it
    }

    integerFieldUpdateOrInsert(
        valuesReadFromSW = todayDateValuesReadFromSW().stepList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = dataViewModel.todayStepListReadFromDB,
        setDayFieldListReadFromDB = setTodayStepListReadFromDB,
        dayFromTableData = todayPhysicalActivityResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = dataViewModel.isTodayStepsListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = dataViewModel.isTodayStepsListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayStepsListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayStepsListInDBAlreadyUpdated,
        TypesTable.STEPS,
        dateData = dataViewModel.todayFormattedDate
    )

    val setTodayDistanceListReadFromDB: (List<Double>) -> Unit = {
        dataViewModel.todayDistanceListReadFromDB = it
    }


    dataViewModel.todayDistanceListReadFromDB = if (todayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = todayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.DISTANCE }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsTodayDistanceListAlreadyInsertedInDB: (Boolean) -> Unit = {
        dataViewModel.isTodayDistanceListAlreadyInsertedInDB = it
    }

    val setIsTodayDistanceListInDBAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.isTodayDistanceListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        valuesReadFromSW = todayDateValuesReadFromSW().distanceList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = dataViewModel.todayDistanceListReadFromDB,
        setDayFieldListReadFromDB = setTodayDistanceListReadFromDB,
        dayFromTableData = todayPhysicalActivityResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = dataViewModel.isTodayDistanceListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = dataViewModel.isTodayDistanceListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayDistanceListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayDistanceListInDBAlreadyUpdated,
        TypesTable.DISTANCE,
        dataViewModel.todayFormattedDate
    )


    val setTodayCaloriesListReadFromDB: (List<Double>) -> Unit = {
        dataViewModel.todayCaloriesListReadFromDB = it
    }


    dataViewModel.todayCaloriesListReadFromDB = if (todayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = todayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.CALORIES }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val setIsTodayCaloriesListAlreadyInsertedInDB: (Boolean) -> Unit = {
        dataViewModel.isTodayCaloriesListAlreadyInsertedInDB = it
    }

    val setIsTodayCaloriesListInDBAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.isTodayCaloriesListInDBAlreadyUpdated = it
    }


    doubleFieldUpdateOrInsert(
        valuesReadFromSW = todayDateValuesReadFromSW().caloriesList,
        dataViewModel = dataViewModel,
        fieldListReadFromDB = dataViewModel.todayCaloriesListReadFromDB,
        setDayFieldListReadFromDB = setTodayCaloriesListReadFromDB,
        dayFromTableData = todayPhysicalActivityResultsFromDB,
        isDayFieldListAlreadyInsertedInDB = dataViewModel.isTodayCaloriesListAlreadyInsertedInDB,
        isDayFieldListInDBAlreadyUpdated = dataViewModel.isTodayCaloriesListInDBAlreadyUpdated,
        setIsDayFieldListAlreadyInsertedInDB = setIsTodayCaloriesListAlreadyInsertedInDB,
        setIsDayFieldListInDBAlreadyUpdated = setIsTodayCaloriesListInDBAlreadyUpdated,
        TypesTable.CALORIES,
        dataViewModel.todayFormattedDate
    )

}