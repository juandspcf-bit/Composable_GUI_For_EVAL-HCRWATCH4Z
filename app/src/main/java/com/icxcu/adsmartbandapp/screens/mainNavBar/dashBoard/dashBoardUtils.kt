package com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard

import android.util.Log
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.data.entities.Field
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

fun integerFieldUpdateOrInsert(
    dataFieldFromSW: List<Int>,
    dataFieldFromDB: List<Field>,
    fieldListState: List<Int>,
    dataViewModel: DataViewModel,
    setFieldListState: (List<Int>) -> Unit,
    isDayFieldListAlreadyInsertedInDB: Boolean,
    isDayFieldListInDBAlreadyUpdated: Boolean,
    setIsDayFieldListAlreadyInsertedInDB: (Boolean) -> Unit,
    setIsDayFieldListInDBAlreadyUpdated: (Boolean) -> Unit,
    typesTableToModify: TypesTable,
    dateData:String,
) {

if(dataFieldFromSW.isEmpty().not() && dataFieldFromDB.isEmpty().not()){
    Log.d("SW_DATA", "which values")
    Log.d("SW_DATA", "valuesReadFromSW.isEmpty().not() ${dataFieldFromSW.isEmpty().not()}")
    Log.d("SW_DATA", "valuesReadFromSW.max()!=0 ${dataFieldFromSW.max()!=0}")

    Log.d("SW_DATA", "dayFromTableData.isEmpty().not() ${dataFieldFromDB.isEmpty().not()}")
    Log.d("SW_DATA", "dayFromTableData[0].id == -1 ${dataFieldFromDB[0].id == -1}")
    Log.d("SW_DATA", "isDayFieldListAlreadyInsertedInDB.not() ${isDayFieldListAlreadyInsertedInDB.not()}")

}else{
    Log.d("SW_DATA", "which values")
    Log.d("SW_DATA", "${dataFieldFromSW.isEmpty()}")
    Log.d("SW_DATA", "max not defined")

    Log.d("SW_DATA", "${dataFieldFromDB.isEmpty().not()}")
    Log.d("SW_DATA", "not defined")
    Log.d("SW_DATA", "${isDayFieldListAlreadyInsertedInDB.not()}")
}





    if (dataFieldFromSW.isEmpty().not() &&
        dataFieldFromSW.max()!=0 &&
        dataFieldFromDB.isEmpty().not() &&
        dataFieldFromDB[0].id != -1 &&
        dataFieldFromSW.toList() != fieldListState.toList() &&
        isDayFieldListInDBAlreadyUpdated.not()
    ) {

        val newValuesList = mutableMapOf<String, String>()
        dataFieldFromSW.forEachIndexed { index, i ->
            newValuesList[index.toString()] = i.toString()
        }

        val listIndex = mutableListOf<Int>()
        dataFieldFromDB.filterIndexed { index, field ->
            if (field.typesTable == typesTableToModify) {
                listIndex.add(index)
                true
            } else false
        }
        if(listIndex.isNotEmpty()){
            dataFieldFromDB[listIndex[0]].data = newValuesList.toString()
            setFieldListState(dataFieldFromSW)
            tableToUpdateSelector(
                typesTableToModify,
                dataViewModel,
                dataFieldFromDB
            )

            setIsDayFieldListInDBAlreadyUpdated(true)
        }


    } else if (dataFieldFromSW.isEmpty().not() &&
        dataFieldFromSW.max()!=0 &&
        dataFieldFromDB.isEmpty().not() &&
        dataFieldFromDB[0].id == -1
        && isDayFieldListAlreadyInsertedInDB.not()
    ) {
        if(dataFieldFromSW.max()==0){
            return
        }
        tableToInsertSelector(
            dataFieldFromSW,
            typesTableToModify,
            dataViewModel,
            dateData
        )

        setIsDayFieldListAlreadyInsertedInDB(true)
    }

}

fun doubleFieldUpdateOrInsert(
    dataFieldFromSW: List<Double>,
    dataFieldFromDB: List<Field>,
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
    if (dataFieldFromSW.isEmpty().not() &&
        dataFieldFromSW.max()!=0.0 &&
        dataFieldFromDB.isEmpty().not() &&
        dataFieldFromDB[0].id != -1 &&
        dataFieldFromSW.toList() != fieldListState.toList() &&
        isDayFieldListInDBAlreadyUpdated.not()
    ) {


        val newValuesList = mutableMapOf<String, String>()
        dataFieldFromSW.forEachIndexed { index, i ->
            newValuesList[index.toString()] = i.toString()
        }

        val listIndex = mutableListOf<Int>()
        dataFieldFromDB.filterIndexed { index, field ->
            if (field.typesTable == typesTableToModify) {
                listIndex.add(index)
                true
            } else false
        }
        if(listIndex.isNotEmpty()){
            dataFieldFromDB[listIndex[0]].data = newValuesList.toString()
            setFieldListState(dataFieldFromSW)
            tableToUpdateSelector(
                typesTableToModify,
                dataViewModel,
                dataFieldFromDB
            )
            setIsDayFieldListInDBAlreadyUpdated(true)
        }




    } else if (dataFieldFromSW.isEmpty().not() &&
        dataFieldFromSW.max()!=0.0 &&
        dataFieldFromDB.isEmpty().not() &&
        dataFieldFromDB[0].id == -1 &&
        isDayFieldListAlreadyInsertedInDB.not()
    ) {

        if(dataFieldFromSW.max()==0.0){
            return
        }
        tableToInsertSelector(
            dataFieldFromSW,
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