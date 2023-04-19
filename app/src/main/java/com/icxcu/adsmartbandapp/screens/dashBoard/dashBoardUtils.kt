package com.icxcu.adsmartbandapp.screens.dashBoard

import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun integerFieldUpdateOrInsert(
    valuesReadFromSW: List<Int>,
    dataViewModel: DataViewModel,
    fieldListReadFromDB: List<Int>,
    setDayFieldListReadFromDB: (List<Int>) -> Unit,
    dayPhysicalActivityData: List<PhysicalActivity>,
    isDayFieldListAlreadyInsertedInDB: Boolean,
    isDayFieldListInDBAlreadyUpdated: Boolean,
    setIsDayFieldListAlreadyInsertedInDB: (Boolean) -> Unit,
    setIsDayFieldListInDBAlreadyUpdated: (Boolean) -> Unit,
    typesTableToModify: TypesTable
) {

    if (valuesReadFromSW.isEmpty().not() &&
        dayPhysicalActivityData.isEmpty().not() &&
        dayPhysicalActivityData[0].physicalActivityId != -1 &&
        valuesReadFromSW.toList() != fieldListReadFromDB.toList() &&
        isDayFieldListInDBAlreadyUpdated.not()
    ) {

        val newValuesList = mutableMapOf<String, String>()
        valuesReadFromSW.forEachIndexed { index, i ->
            newValuesList[index.toString()] = i.toString()
        }

        val listIndex = mutableListOf<Int>()
        dayPhysicalActivityData.filterIndexed { index, physicalActivity ->
            if (physicalActivity.typesTable == typesTableToModify) {
                listIndex.add(index)
                true
            } else false
        }
        dayPhysicalActivityData[listIndex[0]].data = newValuesList.toString()
        setDayFieldListReadFromDB(valuesReadFromSW)
        dataViewModel.updatePhysicalActivityData(dayPhysicalActivityData[0])

        setIsDayFieldListInDBAlreadyUpdated(true)

    } else if (valuesReadFromSW.isEmpty().not() &&
        dayPhysicalActivityData.isEmpty().not() &&
        dayPhysicalActivityData[0].physicalActivityId == -1 && isDayFieldListAlreadyInsertedInDB.not()
    ) {
        val physicalActivity = PhysicalActivity().apply {
            macAddress = dataViewModel.macAddress
            val date = Date()
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateData = formattedDate.format(date)
            typesTable = typesTableToModify
            val newValuesList = mutableMapOf<String, String>()
            valuesReadFromSW.forEachIndexed { index, i ->
                newValuesList[index.toString()] = i.toString()
            }
            data = newValuesList.toString()
        }
        dataViewModel.insertPhysicalActivityData(physicalActivity)
        //stepsAlreadyInserted = true
        setIsDayFieldListAlreadyInsertedInDB(true)
    }

}

fun doubleFieldUpdateOrInsert(
    valuesReadFromSW: List<Double>,
    dataViewModel: DataViewModel,
    fieldListReadFromDB: List<Double>,
    setDayFieldListReadFromDB: (List<Double>) -> Unit,
    dayPhysicalActivityData: List<PhysicalActivity>,
    isDayFieldListAlreadyInsertedInDB: Boolean,
    isDayFieldListInDBAlreadyUpdated: Boolean,
    setIsDayFieldListAlreadyInsertedInDB: (Boolean) -> Unit,
    setIsDayFieldListInDBAlreadyUpdated: (Boolean) -> Unit,
    typesTableToModify: TypesTable
) {

    if (valuesReadFromSW.isEmpty().not() &&
        dayPhysicalActivityData.isEmpty().not() &&
        dayPhysicalActivityData[0].physicalActivityId != -1 &&
        valuesReadFromSW.toList() != fieldListReadFromDB.toList() &&
        isDayFieldListInDBAlreadyUpdated.not()
    ) {

        val newValuesList = mutableMapOf<String, String>()
        valuesReadFromSW.forEachIndexed { index, i ->
            newValuesList[index.toString()] = i.toString()
        }

        val listIndex = mutableListOf<Int>()
        dayPhysicalActivityData.filterIndexed { index, physicalActivity ->
            if (physicalActivity.typesTable == typesTableToModify) {
                listIndex.add(index)
                true
            } else false
        }
        dayPhysicalActivityData[listIndex[0]].data = newValuesList.toString()
        setDayFieldListReadFromDB(valuesReadFromSW)
        dataViewModel.updatePhysicalActivityData(dayPhysicalActivityData[0])

        setIsDayFieldListInDBAlreadyUpdated(true)

    } else if (valuesReadFromSW.isEmpty().not() &&
        dayPhysicalActivityData.isEmpty().not() &&
        dayPhysicalActivityData[0].physicalActivityId == -1 && isDayFieldListAlreadyInsertedInDB.not()
    ) {
        val physicalActivity = PhysicalActivity().apply {
            macAddress = dataViewModel.macAddress
            val date = Date()
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateData = formattedDate.format(date)
            typesTable = typesTableToModify
            val newValuesList = mutableMapOf<String, String>()
            valuesReadFromSW.forEachIndexed { index, i ->
                newValuesList[index.toString()] = i.toString()
            }
            data = newValuesList.toString()
        }
        dataViewModel.insertPhysicalActivityData(physicalActivity)
        //stepsAlreadyInserted = true
        setIsDayFieldListAlreadyInsertedInDB(true)
    }

}