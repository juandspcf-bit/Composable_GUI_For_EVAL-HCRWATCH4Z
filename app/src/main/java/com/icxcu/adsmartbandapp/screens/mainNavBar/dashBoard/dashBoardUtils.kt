package com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard

import android.util.Log
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.data.entities.Field
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

fun integerFieldUpdateOrInsert(
    valuesReadFromSW: List<Int>,
    dayFromTableData: List<Field>,
    dataViewModel: DataViewModel,
    fieldListState: List<Int>,
    setFieldListState: (List<Int>) -> Unit,
    isDayFieldListAlreadyInsertedInDB: Boolean,
    isDayFieldListInDBAlreadyUpdated: Boolean,
    setIsDayFieldListAlreadyInsertedInDB: (Boolean) -> Unit,
    setIsDayFieldListInDBAlreadyUpdated: (Boolean) -> Unit,
    typesTableToModify: TypesTable,
    dateData:String,
) {

if(valuesReadFromSW.isEmpty().not() && dayFromTableData.isEmpty().not()){
    Log.d("SW_DATA", "which values")
    Log.d("SW_DATA", "valuesReadFromSW.isEmpty().not() ${valuesReadFromSW.isEmpty().not()}")
    Log.d("SW_DATA", "valuesReadFromSW.max()!=0 ${valuesReadFromSW.max()!=0}")

    Log.d("SW_DATA", "dayFromTableData.isEmpty().not() ${dayFromTableData.isEmpty().not()}")
    Log.d("SW_DATA", "dayFromTableData[0].id == -1 ${dayFromTableData[0].id == -1}")
    Log.d("SW_DATA", "isDayFieldListAlreadyInsertedInDB.not() ${isDayFieldListAlreadyInsertedInDB.not()}")

}else{
    Log.d("SW_DATA", "which values")
    Log.d("SW_DATA", "${valuesReadFromSW.isEmpty()}")
    Log.d("SW_DATA", "max not defined")

    Log.d("SW_DATA", "${dayFromTableData.isEmpty().not()}")
    Log.d("SW_DATA", "not defined")
    Log.d("SW_DATA", "${isDayFieldListAlreadyInsertedInDB.not()}")
}





    if (valuesReadFromSW.isEmpty().not() &&
        valuesReadFromSW.max()!=0 &&
        dayFromTableData.isEmpty().not() &&
        dayFromTableData[0].id != -1 &&
        valuesReadFromSW.toList() != fieldListState.toList() &&
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
        if(listIndex.isNotEmpty()){
            dayFromTableData[listIndex[0]].data = newValuesList.toString()
            setFieldListState(valuesReadFromSW)
            tableToUpdateSelector(
                typesTableToModify,
                dataViewModel,
                dayFromTableData
            )

            setIsDayFieldListInDBAlreadyUpdated(true)
        }


    } else if (valuesReadFromSW.isEmpty().not() &&
        valuesReadFromSW.max()!=0 &&
        dayFromTableData.isEmpty().not() &&
        dayFromTableData[0].id == -1
        && isDayFieldListAlreadyInsertedInDB.not()
    ) {
        if(valuesReadFromSW.max()==0){
            return
        }
        tableToInsertSelector(
            valuesReadFromSW,
            typesTableToModify,
            dataViewModel,
            dateData
        )

        setIsDayFieldListAlreadyInsertedInDB(true)
    }

}

fun doubleFieldUpdateOrInsert(
    valuesReadFromSW: List<Double>,
    dayFromTableData: List<Field>,
    fieldListState: List<Double>,
    dataViewModel: DataViewModel,
    setFieldListState: (List<Double>) -> Unit,
    isDayFieldListAlreadyInsertedInDB: Boolean,
    isDayFieldListInDBAlreadyUpdated: Boolean,
    setIsDayFieldListAlreadyInsertedInDB: (Boolean) -> Unit,
    setIsDayFieldListInDBAlreadyUpdated: (Boolean) -> Unit,
    typesTableToModify: TypesTable,
    dateData: String
) {
    if (valuesReadFromSW.isEmpty().not() &&
        valuesReadFromSW.max()!=0.0 &&
        dayFromTableData.isEmpty().not() &&
        dayFromTableData[0].id != -1 &&
        valuesReadFromSW.toList() != fieldListState.toList() &&
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
        if(listIndex.isNotEmpty()){
            dayFromTableData[listIndex[0]].data = newValuesList.toString()
            setFieldListState(valuesReadFromSW)
            tableToUpdateSelector(
                typesTableToModify,
                dataViewModel,
                dayFromTableData
            )
            setIsDayFieldListInDBAlreadyUpdated(true)
        }




    } else if (valuesReadFromSW.isEmpty().not() &&
        valuesReadFromSW.max()!=0.0 &&
        dayFromTableData.isEmpty().not() &&
        dayFromTableData[0].id == -1 &&
        isDayFieldListAlreadyInsertedInDB.not()
    ) {

        if(valuesReadFromSW.max()==0.0){
            return
        }
        tableToInsertSelector(
            valuesReadFromSW,
        typesTableToModify,
        dataViewModel,
            dateData
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
        TypesTable.DISTANCE -> dataViewModel.updatePhysicalActivityData(dayFromTableData[1] as PhysicalActivity)
        TypesTable.CALORIES -> dataViewModel.updatePhysicalActivityData(dayFromTableData[2] as PhysicalActivity)
        TypesTable.SYSTOLIC -> dataViewModel.updateBloodPressureData(dayFromTableData[0] as BloodPressure)
        TypesTable.DIASTOLIC -> dataViewModel.updateBloodPressureData(dayFromTableData[1] as BloodPressure)
        TypesTable.HEART_RATE -> dataViewModel.updateHeartRateData(dayFromTableData[0] as HeartRate)
        else -> {}
    }
}

fun tableToInsertSelector(
    valuesReadFromSW: List<Number>,
    typesTableToModify: TypesTable,
    dataViewModel: DataViewModel,
    currentDateData: String
) {

    when (typesTableToModify) {
        TypesTable.STEPS -> {


            val physicalActivity = PhysicalActivity().apply {
                macAddress = dataViewModel.macAddressDeviceBluetooth

                dateData = currentDateData
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
                macAddress = dataViewModel.macAddressDeviceBluetooth

                dateData = currentDateData
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
                macAddress = dataViewModel.macAddressDeviceBluetooth

                dateData = currentDateData
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
                macAddress = dataViewModel.macAddressDeviceBluetooth

                dateData = currentDateData
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
                macAddress = dataViewModel.macAddressDeviceBluetooth

                dateData = currentDateData
                typesTable = typesTableToModify
                val newValuesList = mutableMapOf<String, String>()
                valuesReadFromSW.forEachIndexed { index, i ->
                    newValuesList[index.toString()] = i.toDouble().toString()
                }
                data = newValuesList.toString()
            }
            dataViewModel.insertBloodPressureData(bloodPressure)
        }

        TypesTable.HEART_RATE -> {
            val heartRate = HeartRate().apply {
                macAddress = dataViewModel.macAddressDeviceBluetooth

                dateData = currentDateData
                typesTable = typesTableToModify
                val newValuesList = mutableMapOf<String, String>()
                valuesReadFromSW.forEachIndexed { index, i ->
                    newValuesList[index.toString()] = i.toDouble().toString()
                }
                data = newValuesList.toString()
            }
            dataViewModel.insertHeartRateData(heartRate)
        }

        else -> {}
    }
}