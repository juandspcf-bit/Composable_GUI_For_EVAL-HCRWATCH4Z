package com.icxcu.adsmartbandapp.screens.plotsFields

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
    val todayPhysicalActivityData by dataViewModel.todayPhysicalActivityResults.observeAsState(
        MutableList(0) { PhysicalActivity() }.toList()
    )
    val updateStepList:(List<Int>)->Unit={
        dataViewModel.stepList = it
    }

    val updateStepsAlreadyInserted:(Boolean)->Unit={
        dataViewModel.stepsAlreadyInserted = it
    }

    val updateStepsAlreadyUpdated:(Boolean)->Unit={
        dataViewModel.stepsAlreadyUpdated = it
    }

    dataViewModel.stepList = if (todayPhysicalActivityData.isEmpty().not()) {
        val filter = todayPhysicalActivityData.filter { it.typesTable == TypesTable.STEPS }
        getIntegerListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0 }.toList()
    }


    stepsUpdateOrInsert(
        values = values,
        dataViewModel = dataViewModel,
        stepsList = dataViewModel.stepList,
        updateStepList = updateStepList,
        todayPhysicalActivityData = todayPhysicalActivityData,
        stepsAlreadyInserted = dataViewModel.stepsAlreadyInserted,
        stepsAlreadyUpdated = dataViewModel.stepsAlreadyUpdated,
        updateStepsAlreadyInserted = updateStepsAlreadyInserted,
        updateStepsAlreadyUpdated = updateStepsAlreadyUpdated,
    )

    val updateDistanceList:(List<Double>)->Unit={
        dataViewModel.distanceList = it
    }

    val updateDistanceAlreadyInserted:(Boolean)->Unit={
        dataViewModel.distanceAlreadyInserted = it
    }

    val updateDistanceAlreadyUpdated:(Boolean)->Unit={
        dataViewModel.distanceAlreadyUpdated = it
    }

    dataViewModel.distanceList = if (todayPhysicalActivityData.isEmpty().not()) {
        val filter = todayPhysicalActivityData.filter { it.typesTable == TypesTable.DISTANCE }
        if(filter.isEmpty()){
            MutableList(48) { 0.0 }.toList()
        }else{
            getDoubleListFromStringMap(filter[0].data)
        }
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    distanceUpdateOrInsert(
        values = values,
        dataViewModel = dataViewModel,
        distanceList = dataViewModel.distanceList,
        updateDistanceList = updateDistanceList,
        todayPhysicalActivityData = todayPhysicalActivityData,
        distanceAlreadyInserted = dataViewModel.distanceAlreadyInserted,
        distanceAlreadyUpdated = dataViewModel.distanceAlreadyUpdated,
        updateDistanceAlreadyInserted = updateDistanceAlreadyInserted,
        updateDistanceAlreadyUpdated = updateDistanceAlreadyUpdated,
    )

    val updateCaloriesList:(List<Double>)->Unit={
        dataViewModel.caloriesList = it
    }

    val updateCaloriesAlreadyInserted:(Boolean)->Unit={
        dataViewModel.caloriesAlreadyInserted = it
    }

    val updateCaloriesAlreadyUpdated:(Boolean)->Unit={
        dataViewModel.caloriesAlreadyUpdated = it
    }

    dataViewModel.caloriesList = if (todayPhysicalActivityData.isEmpty().not()) {
        val filter = todayPhysicalActivityData.filter { it.typesTable == TypesTable.CALORIES }
        if(filter.isEmpty()){
            MutableList(48) { 0.0 }.toList()
        }else{
            getDoubleListFromStringMap(filter[0].data)
        }
    } else {
        MutableList(48) { 0.0 }.toList()
    }

/*    caloriesUpdateOrInsert(
        values = values,
        dataViewModel = dataViewModel,
        caloriesList = dataViewModel.caloriesList,
        updateCaloriesList = updateCaloriesList,
        todayPhysicalActivityData = todayPhysicalActivityData,
        caloriesAlreadyInserted = dataViewModel.caloriesAlreadyInserted,
        caloriesAlreadyUpdated = dataViewModel.caloriesAlreadyUpdated,
        updateCaloriesAlreadyInserted = updateCaloriesAlreadyInserted,
        updateCaloriesAlreadyUpdated = updateCaloriesAlreadyUpdated,
    )*/

    doubleFieldUpdateOrInsert(
        fromRepoListDouble = values.caloriesList,
    dataViewModel = dataViewModel,
    fieldDoubleList = dataViewModel.caloriesList,
    updateFieldDoubleList= updateCaloriesList,
    todayPhysicalActivityData= todayPhysicalActivityData,
    doubleFieldAlreadyInserted = dataViewModel.caloriesAlreadyInserted,
    doubleFieldAlreadyUpdated = dataViewModel.caloriesAlreadyUpdated,
    updateDoubleFieldAlreadyInserted = updateCaloriesAlreadyInserted,
    updateDoubleFieldAlreadyUpdated = updateCaloriesAlreadyUpdated,
    typesTableToModify = TypesTable.CALORIES
    )


    PhysicalActivityLayoutScaffold(
        dataViewModel.stepList,
        dataViewModel.distanceList,
        dataViewModel.caloriesList,
        navLambda
    )


}


fun stepsUpdateOrInsert(
    values: Values,
    dataViewModel: DataViewModel,
    stepsList: List<Int>,
    updateStepList:(List<Int>)->Unit,
    todayPhysicalActivityData: List<PhysicalActivity>,
    stepsAlreadyInserted: Boolean,
    stepsAlreadyUpdated: Boolean,
    updateStepsAlreadyInserted:(Boolean)->Unit,
    updateStepsAlreadyUpdated:(Boolean)->Unit,
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

fun distanceUpdateOrInsert(
    values: Values,
    dataViewModel: DataViewModel,
    distanceList: List<Double>,
    updateDistanceList:(List<Double>)->Unit,
    todayPhysicalActivityData: List<PhysicalActivity>,
    distanceAlreadyInserted: Boolean,
    distanceAlreadyUpdated: Boolean,
    updateDistanceAlreadyInserted:(Boolean)->Unit,
    updateDistanceAlreadyUpdated:(Boolean)->Unit,
) {

    if (values.distanceList.isEmpty().not() &&
        todayPhysicalActivityData.isEmpty().not() &&
        todayPhysicalActivityData[0].physicalActivityId != -1 &&
        values.distanceList.toList() != distanceList.toList() &&
        distanceAlreadyUpdated.not()
    ) {

        val newValuesList = mutableMapOf<String, String>()
        values.distanceList.forEachIndexed { index, i ->
            newValuesList[index.toString()] = i.toString()
        }

        val listIndex = mutableListOf<Int>()
        todayPhysicalActivityData.filterIndexed { index, physicalActivity ->
            if (physicalActivity.typesTable == TypesTable.DISTANCE) {
                listIndex.add(index)
                true
            } else false
        }


        todayPhysicalActivityData[listIndex[0]].data = newValuesList.toString()
        updateDistanceList(values.distanceList)
        dataViewModel.updatePhysicalActivityData(todayPhysicalActivityData[0])
        updateDistanceAlreadyUpdated(true)

    } else if (values.distanceList.isEmpty().not() &&
        todayPhysicalActivityData.isEmpty().not() &&
        todayPhysicalActivityData[0].physicalActivityId == -1 && distanceAlreadyInserted.not()
    ) {
        val physicalActivity = PhysicalActivity().apply {
            macAddress = dataViewModel.macAddress
            val date = Date()
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateData = formattedDate.format(date)
            typesTable = TypesTable.DISTANCE
            val newValuesList = mutableMapOf<String, String>()
            values.distanceList.forEachIndexed { index, i ->
                newValuesList[index.toString()] = i.toString()
            }
            data = newValuesList.toString()
        }
        dataViewModel.insertPhysicalActivityData(physicalActivity)
        updateDistanceAlreadyInserted(true)
    }

}

fun caloriesUpdateOrInsert(
    values: Values,
    dataViewModel: DataViewModel,
    caloriesList: List<Double>,
    updateCaloriesList:(List<Double>)->Unit,
    todayPhysicalActivityData: List<PhysicalActivity>,
    caloriesAlreadyInserted: Boolean,
    caloriesAlreadyUpdated: Boolean,
    updateCaloriesAlreadyInserted:(Boolean)->Unit,
    updateCaloriesAlreadyUpdated:(Boolean)->Unit,
) {

    if (values.caloriesList.isEmpty().not() &&
        todayPhysicalActivityData.isEmpty().not() &&
        todayPhysicalActivityData[0].physicalActivityId != -1 &&
        values.caloriesList.toList() != caloriesList.toList() &&
        caloriesAlreadyUpdated.not()
    ) {

        val newValuesList = mutableMapOf<String, String>()
        values.caloriesList.forEachIndexed { index, i ->
            newValuesList[index.toString()] = i.toString()
        }

        val listIndex = mutableListOf<Int>()
        todayPhysicalActivityData.filterIndexed { index, physicalActivity ->
            if (physicalActivity.typesTable == TypesTable.CALORIES) {
                listIndex.add(index)
                true
            } else false
        }

        todayPhysicalActivityData[listIndex[0]].data = newValuesList.toString()
        updateCaloriesList(values.caloriesList)
        dataViewModel.updatePhysicalActivityData(todayPhysicalActivityData[0])
        updateCaloriesAlreadyUpdated(true)

    } else if (values.caloriesList.isEmpty().not() &&
        todayPhysicalActivityData.isEmpty().not() &&
        todayPhysicalActivityData[0].physicalActivityId == -1 && caloriesAlreadyInserted.not()
    ) {
        val physicalActivity = PhysicalActivity().apply {
            macAddress = dataViewModel.macAddress
            val date = Date()
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateData = formattedDate.format(date)
            typesTable = TypesTable.CALORIES
            val newValuesList = mutableMapOf<String, String>()
            values.caloriesList.forEachIndexed { index, i ->
                newValuesList[index.toString()] = i.toString()
            }
            data = newValuesList.toString()
        }
        dataViewModel.insertPhysicalActivityData(physicalActivity)
        //stepsAlreadyInserted = true
        updateCaloriesAlreadyInserted(true)
    }

}

fun doubleFieldUpdateOrInsert(
    fromRepoListDouble: List<Double>,
    dataViewModel: DataViewModel,
    fieldDoubleList: List<Double>,
    updateFieldDoubleList:(List<Double>)->Unit,
    todayPhysicalActivityData: List<PhysicalActivity>,
    doubleFieldAlreadyInserted: Boolean,
    doubleFieldAlreadyUpdated: Boolean,
    updateDoubleFieldAlreadyInserted:(Boolean)->Unit,
    updateDoubleFieldAlreadyUpdated:(Boolean)->Unit,
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



