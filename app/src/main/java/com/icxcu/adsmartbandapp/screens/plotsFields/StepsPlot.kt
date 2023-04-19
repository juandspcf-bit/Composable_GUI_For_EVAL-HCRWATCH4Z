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

    dataViewModel: DataViewModel,
    navLambda: () -> Unit
) {

    val todayValues = {
        dataViewModel.todayDateValues
    }

    //Data state
    val todayPhysicalActivityData by dataViewModel.todayPhysicalActivityResults.observeAsState(
        MutableList(0) { PhysicalActivity() }.toList()
    )
    val todayUpdateStepList: (List<Int>) -> Unit = {
        dataViewModel.todayStepList = it
    }

    val todayUpdateStepsAlreadyInserted: (Boolean) -> Unit = {
        dataViewModel.todayStepsAlreadyInserted = it
    }

    val todayUpdateStepsAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.todayStepsAlreadyUpdated = it
    }

    dataViewModel.todayStepList = if (todayPhysicalActivityData.isEmpty().not()) {
        val filter = todayPhysicalActivityData.filter { it.typesTable == TypesTable.STEPS }
        getIntegerListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0 }.toList()
    }


    stepsUpdateOrInsert(
        values = todayValues(),
        dataViewModel = dataViewModel,
        stepsList = dataViewModel.todayStepList,
        updateStepList = todayUpdateStepList,
        todayPhysicalActivityData = todayPhysicalActivityData,
        stepsAlreadyInserted = dataViewModel.todayStepsAlreadyInserted,
        stepsAlreadyUpdated = dataViewModel.todayStepsAlreadyUpdated,
        updateStepsAlreadyInserted = todayUpdateStepsAlreadyInserted,
        updateStepsAlreadyUpdated = todayUpdateStepsAlreadyUpdated,
    )

    val updateDistanceList: (List<Double>) -> Unit = {
        dataViewModel.todayDistanceList = it
    }

    val updateDistanceAlreadyInserted: (Boolean) -> Unit = {
        dataViewModel.todayDistanceAlreadyInserted = it
    }

    val updateDistanceAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.todayDistanceAlreadyUpdated = it
    }

    dataViewModel.todayDistanceList = if (todayPhysicalActivityData.isEmpty().not()) {
        val filter = todayPhysicalActivityData.filter { it.typesTable == TypesTable.DISTANCE }
        if (filter.isEmpty()) {
            MutableList(48) { 0.0 }.toList()
        } else {
            getDoubleListFromStringMap(filter[0].data)
        }
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    doubleFieldUpdateOrInsert(
        fromRepoListDouble = todayValues().distanceList,
        dataViewModel = dataViewModel,
        fieldDoubleList = dataViewModel.todayDistanceList,
        updateFieldDoubleList = updateDistanceList,
        todayPhysicalActivityData = todayPhysicalActivityData,
        doubleFieldAlreadyInserted = dataViewModel.todayDistanceAlreadyInserted,
        doubleFieldAlreadyUpdated = dataViewModel.todayDistanceAlreadyUpdated,
        updateDoubleFieldAlreadyInserted = updateDistanceAlreadyInserted,
        updateDoubleFieldAlreadyUpdated = updateDistanceAlreadyUpdated,
        typesTableToModify = TypesTable.DISTANCE
    )

    val updateCaloriesList: (List<Double>) -> Unit = {
        dataViewModel.todayCaloriesList = it
    }

    val updateCaloriesAlreadyInserted: (Boolean) -> Unit = {
        dataViewModel.todayCaloriesAlreadyInserted = it
    }

    val updateCaloriesAlreadyUpdated: (Boolean) -> Unit = {
        dataViewModel.todayCaloriesAlreadyUpdated = it
    }

    dataViewModel.todayCaloriesList = if (todayPhysicalActivityData.isEmpty().not()) {
        val filter = todayPhysicalActivityData.filter { it.typesTable == TypesTable.CALORIES }
        if (filter.isEmpty()) {
            MutableList(48) { 0.0 }.toList()
        } else {
            getDoubleListFromStringMap(filter[0].data)
        }
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    doubleFieldUpdateOrInsert(
        fromRepoListDouble = todayValues().caloriesList,
        dataViewModel = dataViewModel,
        fieldDoubleList = dataViewModel.todayCaloriesList,
        updateFieldDoubleList = updateCaloriesList,
        todayPhysicalActivityData = todayPhysicalActivityData,
        doubleFieldAlreadyInserted = dataViewModel.todayCaloriesAlreadyInserted,
        doubleFieldAlreadyUpdated = dataViewModel.todayCaloriesAlreadyUpdated,
        updateDoubleFieldAlreadyInserted = updateCaloriesAlreadyInserted,
        updateDoubleFieldAlreadyUpdated = updateCaloriesAlreadyUpdated,
        typesTableToModify = TypesTable.CALORIES
    )


    val stepsList = {
        dataViewModel.todayStepList
    }
    val distanceList = {
        dataViewModel.todayDistanceList
    }
    val caloriesList = {
        dataViewModel.todayCaloriesList
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



