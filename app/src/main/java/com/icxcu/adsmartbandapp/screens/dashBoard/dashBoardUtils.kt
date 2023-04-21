package com.icxcu.adsmartbandapp.screens.dashBoard

import android.util.Log
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.data.entities.Field
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
    dayFromTableData: List<Field>,
    isDayFieldListAlreadyInsertedInDB: Boolean,
    isDayFieldListInDBAlreadyUpdated: Boolean,
    setIsDayFieldListAlreadyInsertedInDB: (Boolean) -> Unit,
    setIsDayFieldListInDBAlreadyUpdated: (Boolean) -> Unit,
    typesTableToModify: TypesTable
) {

    if (valuesReadFromSW.isEmpty().not() &&
        dayFromTableData.isEmpty().not() &&
        dayFromTableData[0].id != -1 &&
        valuesReadFromSW.toList() != fieldListReadFromDB.toList() &&
        isDayFieldListInDBAlreadyUpdated.not()
    ) {

        val newValuesList = mutableMapOf<String, String>()
        valuesReadFromSW.forEachIndexed { index, i ->
            newValuesList[index.toString()] = i.toString()
        }

        val listIndex = mutableListOf<Int>()
        dayFromTableData.filterIndexed { index, field ->
            if (field.typesTable == typesTableToModify) {
                listIndex.add(index)
                true
            } else false
        }
        dayFromTableData[listIndex[0]].data = newValuesList.toString()
        setDayFieldListReadFromDB(valuesReadFromSW)
        tableToUpdateSelector(
            typesTableToModify,
            dataViewModel,
            dayFromTableData
        )

        setIsDayFieldListInDBAlreadyUpdated(true)

    } else if (valuesReadFromSW.isEmpty().not() &&
        dayFromTableData.isEmpty().not() &&
        dayFromTableData[0].id == -1
        && isDayFieldListAlreadyInsertedInDB.not()
    ) {
        tableToInsertSelector(
            valuesReadFromSW,
            typesTableToModify,
            dataViewModel,
        )

        setIsDayFieldListAlreadyInsertedInDB(true)
    }

}

fun doubleFieldUpdateOrInsert(
    valuesReadFromSW: List<Double>,
    dataViewModel: DataViewModel,
    fieldListReadFromDB: List<Double>,
    setDayFieldListReadFromDB: (List<Double>) -> Unit,
    dayFromTableData: List<Field>,
    isDayFieldListAlreadyInsertedInDB: Boolean,
    isDayFieldListInDBAlreadyUpdated: Boolean,
    setIsDayFieldListAlreadyInsertedInDB: (Boolean) -> Unit,
    setIsDayFieldListInDBAlreadyUpdated: (Boolean) -> Unit,
    typesTableToModify: TypesTable
) {
    if (valuesReadFromSW.isEmpty().not() &&
        dayFromTableData.isEmpty().not() &&
        dayFromTableData[0].id != -1 &&
        valuesReadFromSW.toList() != fieldListReadFromDB.toList() &&
        isDayFieldListInDBAlreadyUpdated.not()
    ) {


        val newValuesList = mutableMapOf<String, String>()
        valuesReadFromSW.forEachIndexed { index, i ->
            newValuesList[index.toString()] = i.toString()
        }

        val listIndex = mutableListOf<Int>()
        dayFromTableData.filterIndexed { index, field ->
            if (field.typesTable == typesTableToModify) {
                listIndex.add(index)
                true
            } else false
        }
        dayFromTableData[listIndex[0]].data = newValuesList.toString()
        setDayFieldListReadFromDB(valuesReadFromSW)

        tableToUpdateSelector(
            typesTableToModify,
            dataViewModel,
            dayFromTableData
        )
        setIsDayFieldListInDBAlreadyUpdated(true)

    } else if (valuesReadFromSW.isEmpty().not() &&
        dayFromTableData.isEmpty().not() &&
        dayFromTableData[0].id == -1 &&
        isDayFieldListAlreadyInsertedInDB.not()
    ) {

        Log.d("Comparision", "doubleFieldUpdateOrInsert:${dayFromTableData[0].id}, ${isDayFieldListAlreadyInsertedInDB} ${valuesReadFromSW.toList() != fieldListReadFromDB.toList()}, $typesTableToModify")
        tableToInsertSelector(
            valuesReadFromSW,
        typesTableToModify,
        dataViewModel,
        )
        setIsDayFieldListAlreadyInsertedInDB(true)
    }

}

fun tableToUpdateSelector(
    typesTableToModify: TypesTable,
    dataViewModel: DataViewModel,
    dayFromTableData: List<Field>,
) {
    when (typesTableToModify) {
        TypesTable.STEPS -> dataViewModel.updatePhysicalActivityData(dayFromTableData[0] as PhysicalActivity)
        TypesTable.DISTANCE -> dataViewModel.updatePhysicalActivityData(dayFromTableData[0] as PhysicalActivity)
        TypesTable.CALORIES -> dataViewModel.updatePhysicalActivityData(dayFromTableData[0] as PhysicalActivity)
        TypesTable.SYSTOLIC -> dataViewModel.updateBloodPressureData(dayFromTableData[0] as BloodPressure)
        TypesTable.DIASTOLIC -> dataViewModel.updateBloodPressureData(dayFromTableData[0] as BloodPressure)
    }
}

fun tableToInsertSelector(
    valuesReadFromSW: List<Number>,
    typesTableToModify: TypesTable,
    dataViewModel: DataViewModel,
) {
    when (typesTableToModify) {
        TypesTable.STEPS -> {
            val physicalActivity = PhysicalActivity().apply {
                macAddress = dataViewModel.macAddress
                val date = Date()
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                dateData = formattedDate.format(date)
                typesTable = typesTableToModify
                val newValuesList = mutableMapOf<String, String>()
                valuesReadFromSW.forEachIndexed { index, i ->
                    newValuesList[index.toString()] = i.toInt().toString()
                }
                data = newValuesList.toString()
            }
            dataViewModel.insertPhysicalActivityData(physicalActivity)
        }

        TypesTable.DISTANCE -> {
            val physicalActivity = PhysicalActivity().apply {
                macAddress = dataViewModel.macAddress
                val date = Date()
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                dateData = formattedDate.format(date)
                typesTable = typesTableToModify
                val newValuesList = mutableMapOf<String, String>()
                valuesReadFromSW.forEachIndexed { index, i ->
                    newValuesList[index.toString()] = i.toDouble().toString()
                }
                data = newValuesList.toString()
            }
            dataViewModel.insertPhysicalActivityData(physicalActivity)
        }
        TypesTable.CALORIES -> {
            val physicalActivity = PhysicalActivity().apply {
                macAddress = dataViewModel.macAddress
                val date = Date()
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                dateData = formattedDate.format(date)
                typesTable = typesTableToModify
                val newValuesList = mutableMapOf<String, String>()
                valuesReadFromSW.forEachIndexed { index, i ->
                    newValuesList[index.toString()] = i.toDouble().toString()
                }
                data = newValuesList.toString()
            }
            dataViewModel.insertPhysicalActivityData(physicalActivity)
        }
        TypesTable.SYSTOLIC -> {
            val bloodPressure = BloodPressure().apply {
                macAddress = dataViewModel.macAddress
                val date = Date()
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                dateData = formattedDate.format(date)
                typesTable = typesTableToModify
                val newValuesList = mutableMapOf<String, String>()
                valuesReadFromSW.forEachIndexed { index, i ->
                    newValuesList[index.toString()] = i.toDouble().toString()
                }
                data = newValuesList.toString()
            }
            dataViewModel.insertBloodPressureData(bloodPressure)
        }
        TypesTable.DIASTOLIC -> {
            val bloodPressure = BloodPressure().apply {
                macAddress = dataViewModel.macAddress
                val date = Date()
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                dateData = formattedDate.format(date)
                typesTable = typesTableToModify
                val newValuesList = mutableMapOf<String, String>()
                valuesReadFromSW.forEachIndexed { index, i ->
                    newValuesList[index.toString()] = i.toDouble().toString()
                }
                data = newValuesList.toString()
            }
            dataViewModel.insertBloodPressureData(bloodPressure)
        }
    }
}