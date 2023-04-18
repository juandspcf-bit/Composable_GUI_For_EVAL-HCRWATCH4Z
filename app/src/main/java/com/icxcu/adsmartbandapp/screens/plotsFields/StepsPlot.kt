package com.icxcu.adsmartbandapp.screens.plotsFields

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.repositories.Values
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun PhysicalActivityInfo(
    values: Values,
    dataViewModel: DataViewModel,
    navLambda: () -> Unit
) {

    //Data state
    val dayPhysicalActivityData by dataViewModel.dayPhysicalActivityResults.observeAsState(
        MutableList(0) { PhysicalActivity() }.toList()
    )
    val updateStepList: (List<Int>) -> Unit = {
        dataViewModel.stepList = it
    }

    val updateStepsAlreadyInserted: (Boolean) -> Unit = {
        dataViewModel.stepsAlreadyInserted = it
    }

    val updateStepsAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.stepsAlreadyUpdated = it
    }

    dataViewModel.stepList = if (dayPhysicalActivityData.isEmpty().not()) {
        val filter = dayPhysicalActivityData.filter { it.typesTable == TypesTable.STEPS }
        getIntegerListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0 }.toList()
    }


    stepsUpdateOrInsert(
        values = values,
        dataViewModel = dataViewModel,
        stepsList = dataViewModel.stepList,
        updateStepList = updateStepList,
        todayPhysicalActivityData = dayPhysicalActivityData,
        stepsAlreadyInserted = dataViewModel.stepsAlreadyInserted,
        stepsAlreadyUpdated = dataViewModel.stepsAlreadyUpdated,
        updateStepsAlreadyInserted = updateStepsAlreadyInserted,
        updateStepsAlreadyUpdated = updateStepsAlreadyUpdated,
    )

    val updateDistanceList: (List<Double>) -> Unit = {
        dataViewModel.distanceList = it
    }

    val updateDistanceAlreadyInserted: (Boolean) -> Unit = {
        dataViewModel.distanceAlreadyInserted = it
    }

    val updateDistanceAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.distanceAlreadyUpdated = it
    }

    dataViewModel.distanceList = if (dayPhysicalActivityData.isEmpty().not()) {
        val filter = dayPhysicalActivityData.filter { it.typesTable == TypesTable.DISTANCE }
        if (filter.isEmpty()) {
            MutableList(48) { 0.0 }.toList()
        } else {
            getDoubleListFromStringMap(filter[0].data)
        }
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    doubleFieldUpdateOrInsert(
        fromRepoListDouble = values.distanceList,
        dataViewModel = dataViewModel,
        fieldDoubleList = dataViewModel.distanceList,
        updateFieldDoubleList = updateDistanceList,
        todayPhysicalActivityData = dayPhysicalActivityData,
        doubleFieldAlreadyInserted = dataViewModel.distanceAlreadyInserted,
        doubleFieldAlreadyUpdated = dataViewModel.distanceAlreadyUpdated,
        updateDoubleFieldAlreadyInserted = updateDistanceAlreadyInserted,
        updateDoubleFieldAlreadyUpdated = updateDistanceAlreadyUpdated,
        typesTableToModify = TypesTable.DISTANCE
    )

    val updateCaloriesList: (List<Double>) -> Unit = {
        dataViewModel.caloriesList = it
    }

    val updateCaloriesAlreadyInserted: (Boolean) -> Unit = {
        dataViewModel.caloriesAlreadyInserted = it
    }

    val updateCaloriesAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.caloriesAlreadyUpdated = it
    }

    dataViewModel.caloriesList = if (dayPhysicalActivityData.isEmpty().not()) {
        val filter = dayPhysicalActivityData.filter { it.typesTable == TypesTable.CALORIES }
        if (filter.isEmpty()) {
            MutableList(48) { 0.0 }.toList()
        } else {
            getDoubleListFromStringMap(filter[0].data)
        }
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    doubleFieldUpdateOrInsert(
        fromRepoListDouble = values.caloriesList,
        dataViewModel = dataViewModel,
        fieldDoubleList = dataViewModel.caloriesList,
        updateFieldDoubleList = updateCaloriesList,
        todayPhysicalActivityData = dayPhysicalActivityData,
        doubleFieldAlreadyInserted = dataViewModel.caloriesAlreadyInserted,
        doubleFieldAlreadyUpdated = dataViewModel.caloriesAlreadyUpdated,
        updateDoubleFieldAlreadyInserted = updateCaloriesAlreadyInserted,
        updateDoubleFieldAlreadyUpdated = updateCaloriesAlreadyUpdated,
        typesTableToModify = TypesTable.CALORIES
    )


    val stepsList = {
        dataViewModel.stepList
    }
    val distanceList = {
        dataViewModel.distanceList
    }
    val caloriesList = {
        dataViewModel.caloriesList
    }

    //DialogDatePicker State
    val stateShowDialogDatePickerValue = {
        dataViewModel.stateShowDialogDatePicker
    }
    val stateShowDialogDatePickerSetter:(Boolean) -> Unit = { value ->
        dataViewModel.stateShowDialogDatePicker = value
    }

    val stateMiliSecondsDateDialogDatePicker = {
        dataViewModel.stateMiliSecondsDateDialogDatePicker
    }
    val stateMiliSecondsDateDialogDatePickerSetter:(Long) -> Unit = { value ->
        dataViewModel.stateMiliSecondsDateDialogDatePicker = value
        Log.d(
            "Dialog",
            "DatePickerDialogSample: $value"
        )

        val date = Date(value)
        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateData = formattedDate.format(date)
        Log.d(
            "Dialog",
            "DatePickerDialogSample: $dateData"
        )

    }

    PhysicalActivityLayoutScaffold(
        stepsList,
        distanceList,
        caloriesList,
        stateShowDialogDatePickerSetter,
        stateShowDialogDatePickerValue,
        stateMiliSecondsDateDialogDatePicker,
        stateMiliSecondsDateDialogDatePickerSetter,
        navLambda
    )

}


fun stepsUpdateOrInsert(
    values: Values,
    dataViewModel: DataViewModel,
    stepsList: List<Int>,
    updateStepList: (List<Int>) -> Unit,
    todayPhysicalActivityData: List<PhysicalActivity>,
    stepsAlreadyInserted: Boolean,
    stepsAlreadyUpdated: Boolean,
    updateStepsAlreadyInserted: (Boolean) -> Unit,
    updateStepsAlreadyUpdated: (Boolean) -> Unit,
) {

    if (values.stepList.isEmpty().not() &&
        todayPhysicalActivityData.isEmpty().not() &&
        todayPhysicalActivityData[0].physicalActivityId != -1 &&
        values.stepList.toList() != stepsList.toList() &&
        stepsAlreadyUpdated.not()
    ) {

        val newValuesList = mutableMapOf<String, String>()
        values.stepList.forEachIndexed { index, i ->
            newValuesList[index.toString()] = i.toString()
        }

        val listIndex = mutableListOf<Int>()
        todayPhysicalActivityData.filterIndexed { index, physicalActivity ->
            if (physicalActivity.typesTable == TypesTable.STEPS) {
                listIndex.add(index)
                true
            } else false
        }
        todayPhysicalActivityData[listIndex[0]].data = newValuesList.toString()
        //stepsList = values.stepList
        updateStepList(values.stepList)
        dataViewModel.updatePhysicalActivityData(todayPhysicalActivityData[0])

        //stepsAlreadyUpdated = true
        updateStepsAlreadyUpdated(true)

    } else if (values.stepList.isEmpty().not() &&
        todayPhysicalActivityData.isEmpty().not() &&
        todayPhysicalActivityData[0].physicalActivityId == -1 && stepsAlreadyInserted.not()
    ) {
        val physicalActivity = PhysicalActivity().apply {
            macAddress = dataViewModel.macAddress
            val date = Date()
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateData = formattedDate.format(date)
            typesTable = TypesTable.STEPS
            val newValuesList = mutableMapOf<String, String>()
            values.stepList.forEachIndexed { index, i ->
                newValuesList[index.toString()] = i.toString()
            }
            data = newValuesList.toString()
        }
        dataViewModel.insertPhysicalActivityData(physicalActivity)
        //stepsAlreadyInserted = true
        updateStepsAlreadyInserted(true)
    }

}

fun doubleFieldUpdateOrInsert(
    fromRepoListDouble: List<Double>,
    dataViewModel: DataViewModel,
    fieldDoubleList: List<Double>,
    updateFieldDoubleList: (List<Double>) -> Unit,
    todayPhysicalActivityData: List<PhysicalActivity>,
    doubleFieldAlreadyInserted: Boolean,
    doubleFieldAlreadyUpdated: Boolean,
    updateDoubleFieldAlreadyInserted: (Boolean) -> Unit,
    updateDoubleFieldAlreadyUpdated: (Boolean) -> Unit,
    typesTableToModify: TypesTable
) {

    if (fromRepoListDouble.isEmpty().not() &&
        todayPhysicalActivityData.isEmpty().not() &&
        todayPhysicalActivityData[0].physicalActivityId != -1 &&
        fromRepoListDouble.toList() != fieldDoubleList.toList() &&
        doubleFieldAlreadyUpdated.not()
    ) {

        val newValuesList = mutableMapOf<String, String>()
        fromRepoListDouble.forEachIndexed { index, i ->
            newValuesList[index.toString()] = i.toString()
        }

        val listIndex = mutableListOf<Int>()
        todayPhysicalActivityData.filterIndexed { index, physicalActivity ->
            if (physicalActivity.typesTable == typesTableToModify) {
                listIndex.add(index)
                true
            } else false
        }

        todayPhysicalActivityData[listIndex[0]].data = newValuesList.toString()
        updateFieldDoubleList(fromRepoListDouble)
        dataViewModel.updatePhysicalActivityData(todayPhysicalActivityData[0])
        updateDoubleFieldAlreadyUpdated(true)

    } else if (fromRepoListDouble.isEmpty().not() &&
        todayPhysicalActivityData.isEmpty().not() &&
        todayPhysicalActivityData[0].physicalActivityId == -1 && doubleFieldAlreadyInserted.not()
    ) {
        val physicalActivity = PhysicalActivity().apply {
            macAddress = dataViewModel.macAddress
            val date = Date()
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateData = formattedDate.format(date)
            typesTable = typesTableToModify
            val newValuesList = mutableMapOf<String, String>()
            fromRepoListDouble.forEachIndexed { index, i ->
                newValuesList[index.toString()] = i.toString()
            }
            data = newValuesList.toString()
        }
        dataViewModel.insertPhysicalActivityData(physicalActivity)
        //stepsAlreadyInserted = true
        updateDoubleFieldAlreadyInserted(true)
    }

}



