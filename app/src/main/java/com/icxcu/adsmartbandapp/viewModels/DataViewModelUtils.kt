package com.icxcu.adsmartbandapp.viewModels

import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.data.entities.Field
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity

fun notFoundPhysicalActivityList(queryDate: String, queryMacAddress: String):List<PhysicalActivity>{
    val stepsActivity = PhysicalActivity().apply {
        id = -1
        macAddress = queryMacAddress
        dateData = queryDate

        val newValuesList = mutableMapOf<String, String>()
        MutableList(48) { 0 }.forEachIndexed { index, i ->
            newValuesList[index.toString()] = i.toString()
        }
        data = newValuesList.toString()
        typesTable = TypesTable.STEPS
    }

    val distanceActivity = PhysicalActivity().apply {
        id = -1
        macAddress = queryMacAddress
        dateData = queryDate

        val newValuesList = mutableMapOf<String, String>()
        MutableList(48) { 0.0 }.forEachIndexed { index, i ->
            newValuesList[index.toString()] = i.toString()
        }
        data = newValuesList.toString()
        typesTable = TypesTable.DISTANCE
    }

    val caloriesListActivity = PhysicalActivity().apply {
        id = -1
        macAddress = queryMacAddress
        dateData = queryDate

        val newValuesList = mutableMapOf<String, String>()
        MutableList(48) { 0.0 }.forEachIndexed { index, i ->
            newValuesList[index.toString()] = i.toString()
        }
        data = newValuesList.toString()
        typesTable = TypesTable.CALORIES
    }
    return listOf(stepsActivity, distanceActivity, caloriesListActivity)
}

fun notFoundBloodPressureList(queryDate: String, queryMacAddress: String):List<BloodPressure>{
        val bloodPressureS = BloodPressure().apply {
            id = -1
            macAddress = queryMacAddress
            dateData = queryDate

            val newValuesList = mutableMapOf<String, String>()
            MutableList(48) { 0.0 }.forEachIndexed { index, i ->
                newValuesList[index.toString()] = i.toString()
            }
            data = newValuesList.toString()
            typesTable = TypesTable.SYSTOLIC
        }

        val bloodPressureD = BloodPressure().apply {
            id = -1
            macAddress = queryMacAddress
            dateData = queryDate

            val newValuesList = mutableMapOf<String, String>()
            MutableList(48) { 0.0 }.forEachIndexed { index, i ->
                newValuesList[index.toString()] = i.toString()
            }
            data = newValuesList.toString()
            typesTable = TypesTable.DIASTOLIC
        }


        return listOf(bloodPressureS, bloodPressureD)
}

fun notFoundHeartRateList(queryDate: String, queryMacAddress: String):List<HeartRate>{
    val heartRateS = HeartRate().apply {
        id = -1
        macAddress = queryMacAddress
        dateData = queryDate

        val newValuesList = mutableMapOf<String, String>()
        MutableList(48) { 0.0 }.forEachIndexed { index, i ->
            newValuesList[index.toString()] = i.toString()
        }
        data = newValuesList.toString()
        typesTable = TypesTable.HEART_RATE
    }


    return listOf(heartRateS)
}



fun integerFieldUpdateOrInsert(
    dataFieldFromSW: List<Int>,
    dataFieldFromDB: List<Field>,
    fieldListState: List<Int>,
    mainNavigationViewModel: MainNavigationViewModel,
    //setFieldListState: (List<Int>) -> Unit,
    typesTableToModify: TypesTable,
    dateData:String,
) {

    if (dataFieldFromSW.isEmpty().not() &&
        dataFieldFromSW.max()!=0 &&
        //dataFieldFromDB.isEmpty().not() &&
        dataFieldFromDB[0].id != -1 &&
        dataFieldFromSW.toList() != fieldListState.toList()
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
            //setFieldListState(dataFieldFromSW)
            tableToUpdateSelector(
                typesTableToModify,
                mainNavigationViewModel,
                dataFieldFromDB
            )

        }


    } else if (dataFieldFromSW.isEmpty().not() &&
        dataFieldFromSW.max()!=0 &&
        //dataFieldFromDB.isEmpty().not() &&
        dataFieldFromDB[0].id == -1
    ) {
        if(dataFieldFromSW.max()==0){
            return
        }
        tableToInsertSelector(
            dataFieldFromSW,
            typesTableToModify,
            mainNavigationViewModel,
            dateData
        )

    }

}

fun doubleFieldUpdateOrInsert(
    dataFieldFromSW: List<Double>,
    dataFieldFromDB: List<Field>,
    fieldListState: List<Double>,
    mainNavigationViewModel: MainNavigationViewModel,
    typesTableToModify: TypesTable,
    dateData: String
) {
    if (dataFieldFromSW.isEmpty().not() &&
        dataFieldFromSW.max()!=0.0 &&
        //dataFieldFromDB.isEmpty().not() &&
        dataFieldFromDB[0].id != -1 &&
        dataFieldFromSW.toList() != fieldListState.toList()
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
            //setFieldListState(dataFieldFromSW)
            tableToUpdateSelector(
                typesTableToModify,
                mainNavigationViewModel,
                dataFieldFromDB
            )
        }




    } else if (dataFieldFromSW.isEmpty().not() &&
        dataFieldFromSW.max()!=0.0 &&
        //dataFieldFromDB.isEmpty().not() &&
        dataFieldFromDB[0].id == -1
    ) {

        if(dataFieldFromSW.max()==0.0){
            return
        }
        tableToInsertSelector(
            dataFieldFromSW,
            typesTableToModify,
            mainNavigationViewModel,
            dateData
        )
    }

}

fun tableToUpdateSelector(
    typesTableToModify: TypesTable,
    mainNavigationViewModel: MainNavigationViewModel,
    dayFromTableData: List<Field>,
) {
    when (typesTableToModify) {
        TypesTable.STEPS -> mainNavigationViewModel.updatePhysicalActivityData(dayFromTableData[0] as PhysicalActivity)
        TypesTable.DISTANCE -> mainNavigationViewModel.updatePhysicalActivityData(dayFromTableData[1] as PhysicalActivity)
        TypesTable.CALORIES -> mainNavigationViewModel.updatePhysicalActivityData(dayFromTableData[2] as PhysicalActivity)
        TypesTable.SYSTOLIC -> mainNavigationViewModel.updateBloodPressureData(dayFromTableData[0] as BloodPressure)
        TypesTable.DIASTOLIC -> mainNavigationViewModel.updateBloodPressureData(dayFromTableData[1] as BloodPressure)
        TypesTable.HEART_RATE -> mainNavigationViewModel.updateHeartRateData(dayFromTableData[0] as HeartRate)
        else -> {}
    }
}

fun tableToInsertSelector(
    valuesReadFromSW: List<Number>,
    typesTableToModify: TypesTable,
    mainNavigationViewModel: MainNavigationViewModel,
    currentDateData: String
) {

    when (typesTableToModify) {
        TypesTable.STEPS -> {


            val physicalActivity = PhysicalActivity().apply {
                macAddress = mainNavigationViewModel.macAddressDeviceBluetooth

                dateData = currentDateData
                typesTable = typesTableToModify
                val newValuesList = mutableMapOf<String, String>()
                valuesReadFromSW.forEachIndexed { index, i ->
                    newValuesList[index.toString()] = i.toInt().toString()
                }
                data = newValuesList.toString()
            }
            mainNavigationViewModel.insertPhysicalActivityData(physicalActivity)
        }

        TypesTable.DISTANCE -> {
            val physicalActivity = PhysicalActivity().apply {
                macAddress = mainNavigationViewModel.macAddressDeviceBluetooth

                dateData = currentDateData
                typesTable = typesTableToModify
                val newValuesList = mutableMapOf<String, String>()
                valuesReadFromSW.forEachIndexed { index, i ->
                    newValuesList[index.toString()] = i.toDouble().toString()
                }
                data = newValuesList.toString()
            }
            mainNavigationViewModel.insertPhysicalActivityData(physicalActivity)
        }
        TypesTable.CALORIES -> {
            val physicalActivity = PhysicalActivity().apply {
                macAddress = mainNavigationViewModel.macAddressDeviceBluetooth

                dateData = currentDateData
                typesTable = typesTableToModify
                val newValuesList = mutableMapOf<String, String>()
                valuesReadFromSW.forEachIndexed { index, i ->
                    newValuesList[index.toString()] = i.toDouble().toString()
                }
                data = newValuesList.toString()
            }
            mainNavigationViewModel.insertPhysicalActivityData(physicalActivity)
        }
        TypesTable.SYSTOLIC -> {
            val bloodPressure = BloodPressure().apply {
                macAddress = mainNavigationViewModel.macAddressDeviceBluetooth

                dateData = currentDateData
                typesTable = typesTableToModify
                val newValuesList = mutableMapOf<String, String>()
                valuesReadFromSW.forEachIndexed { index, i ->
                    newValuesList[index.toString()] = i.toDouble().toString()
                }
                data = newValuesList.toString()
            }
            mainNavigationViewModel.insertBloodPressureData(bloodPressure)
        }
        TypesTable.DIASTOLIC -> {
            val bloodPressure = BloodPressure().apply {
                macAddress = mainNavigationViewModel.macAddressDeviceBluetooth

                dateData = currentDateData
                typesTable = typesTableToModify
                val newValuesList = mutableMapOf<String, String>()
                valuesReadFromSW.forEachIndexed { index, i ->
                    newValuesList[index.toString()] = i.toDouble().toString()
                }
                data = newValuesList.toString()
            }
            mainNavigationViewModel.insertBloodPressureData(bloodPressure)
        }

        TypesTable.HEART_RATE -> {
            val heartRate = HeartRate().apply {
                macAddress = mainNavigationViewModel.macAddressDeviceBluetooth

                dateData = currentDateData
                typesTable = typesTableToModify
                val newValuesList = mutableMapOf<String, String>()
                valuesReadFromSW.forEachIndexed { index, i ->
                    newValuesList[index.toString()] = i.toDouble().toString()
                }
                data = newValuesList.toString()
            }
            mainNavigationViewModel.insertHeartRateData(heartRate)
        }

        else -> {}
    }
}