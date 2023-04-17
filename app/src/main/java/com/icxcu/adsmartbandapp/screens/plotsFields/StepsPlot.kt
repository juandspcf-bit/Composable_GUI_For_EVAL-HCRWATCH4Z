package com.icxcu.adsmartbandapp.screens.plotsFields

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.data.MockData
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.repositories.Values
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*


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

    Log.d("DataFRomDB", "PhysicalActivityInfo: $todayPhysicalActivityData")

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

    caloriesUpdateOrInsert(
        values = values,
        dataViewModel = dataViewModel,
        caloriesList = dataViewModel.caloriesList,
        updateCaloriesList = updateCaloriesList,
        todayPhysicalActivityData = todayPhysicalActivityData,
        caloriesAlreadyInserted = dataViewModel.caloriesAlreadyInserted,
        caloriesAlreadyUpdated = dataViewModel.caloriesAlreadyUpdated,
        updateCaloriesAlreadyInserted = updateCaloriesAlreadyInserted,
        updateCaloriesAlreadyUpdated = updateCaloriesAlreadyUpdated,
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
        Log.d("DDDDDD", "distanceUpdateOrInsert: $todayPhysicalActivityData")
        //if(listIndex.isEmpty())return
        todayPhysicalActivityData[listIndex[0]].data = newValuesList.toString()
        //stepsList = values.stepList
        updateDistanceList(values.distanceList)
        dataViewModel.updatePhysicalActivityData(todayPhysicalActivityData[0])

        //stepsAlreadyUpdated = true
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
        //stepsAlreadyInserted = true
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
        Log.d("DDDDDD", "distanceUpdateOrInsert: $todayPhysicalActivityData")
        //if(listIndex.isEmpty())return
        todayPhysicalActivityData[listIndex[0]].data = newValuesList.toString()
        //stepsList = values.stepList
        updateCaloriesList(values.caloriesList)
        dataViewModel.updatePhysicalActivityData(todayPhysicalActivityData[0])

        //stepsAlreadyUpdated = true
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




fun getIntegerListFromStringMap(map: String): List<Int> {
    val newMap = map.subSequence(1, map.length - 1)
    val newMapD = newMap.split(", ")
    val stepsLists = MutableList(48) { 0 }
    newMapD.forEachIndexed { indexO, s ->
        val split = s.split("=")
        val index = split[0].toInt()
        val value = split[1].toInt()
        stepsLists[index] = value
    }
    Log.d("DIV", "getDataFromStringMap: $stepsLists")
    return stepsLists.toList()
}

fun getDoubleListFromStringMap(map: String): List<Double> {
    val newMap = map.subSequence(1, map.length - 1)
    val newMapD = newMap.split(", ")
    val stepsLists = MutableList(48) { 0.0 }
    newMapD.forEachIndexed { indexO, s ->
        val split = s.split("=")
        val index = split[0].toInt()
        val value = split[1].toDouble()
        stepsLists[index] = value
    }
    Log.d("DIV", "getDataFromStringMap: $stepsLists")
    return stepsLists.toList()
}

