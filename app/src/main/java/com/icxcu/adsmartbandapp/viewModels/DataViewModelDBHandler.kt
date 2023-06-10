package com.icxcu.adsmartbandapp.viewModels

import android.util.Log
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.repositories.DBRepository
import com.icxcu.adsmartbandapp.repositories.Values
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getIntegerListFromStringMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

fun updateOrInsertPhysicalActivityDataBase(
    values: Values,
    queryDate: String,
    queryMacAddress: String,
    dataViewModel: DataViewModel,
    dbRepository: DBRepository
) {
    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {

        val dataDeferred = async {
            dbRepository.getDayPhysicalActivityWithCoroutine(queryDate, queryMacAddress)
        }

        var dataCoroutineFromDB = dataDeferred.await()

        dataCoroutineFromDB = dataCoroutineFromDB.ifEmpty {
            notFoundPhysicalActivityList(queryDate, queryMacAddress)
        }

        val todayStepList = if (dataCoroutineFromDB.isEmpty().not()) {
            val filter = dataCoroutineFromDB.filter { it.typesTable == TypesTable.STEPS }
            if (filter.isNotEmpty()) {
                getIntegerListFromStringMap(filter[0].data)
            } else {
                MutableList(48) { 0 }.toList()
            }
        } else {
            MutableList(48) { 0 }.toList()
        }


        val todayDistanceList = if (dataCoroutineFromDB.isEmpty().not()) {
            val filter = dataCoroutineFromDB.filter { it.typesTable == TypesTable.DISTANCE }
            if (filter.isNotEmpty()) {
                getDoubleListFromStringMap(filter[0].data)
            } else {
                MutableList(48) { 0.0 }.toList()
            }

        } else {
            MutableList(48) { 0.0 }.toList()
        }

        val todayCaloriesList = if (dataCoroutineFromDB.isEmpty().not()) {
            val filter = dataCoroutineFromDB.filter { it.typesTable == TypesTable.CALORIES }
            if (filter.isNotEmpty()) {
                getDoubleListFromStringMap(filter[0].data)
            } else {
                MutableList(48) { 0.0 }.toList()
            }
        } else {
            MutableList(48) { 0.0 }.toList()
        }


        integerFieldUpdateOrInsert(
            dataFieldFromSW = values.stepList,
            dataFieldFromDB = dataCoroutineFromDB,
            fieldListState = todayStepList,
            dataViewModel = dataViewModel,
            typesTableToModify = TypesTable.STEPS,
            dateData = queryDate,
        )

        doubleFieldUpdateOrInsert(
            dataFieldFromSW = values.distanceList,
            dataFieldFromDB = dataCoroutineFromDB,
            fieldListState = todayDistanceList,
            dataViewModel = dataViewModel,
            typesTableToModify = TypesTable.DISTANCE,
            dateData = queryDate,
        )

        doubleFieldUpdateOrInsert(
            dataFieldFromSW = values.caloriesList,
            dataFieldFromDB = dataCoroutineFromDB,
            fieldListState = todayCaloriesList,
            dataViewModel = dataViewModel,
            typesTableToModify = TypesTable.CALORIES,
            dateData = queryDate,
        )


    }

}


fun updateOrInsertBloodPressureDataBase(
    values: Values,
    queryDate: String,
    queryMacAddress: String,
    dataViewModel: DataViewModel,
    dbRepository: DBRepository
) {
    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {

        val dataDeferred = async {
            dbRepository.getDayBloodPressureWithCoroutine(queryDate, queryMacAddress)
        }

        var dataCoroutineFromDB = dataDeferred.await()

        dataCoroutineFromDB = dataCoroutineFromDB.ifEmpty {
            notFoundBloodPressureList(queryDate, queryMacAddress)
        }

        val systolicList = if (dataCoroutineFromDB.isEmpty().not()) {
            Log.d("MY-DATA", "TodayPhysicalActivityDBHandler: $dataCoroutineFromDB")
            val filter = dataCoroutineFromDB.filter { it.typesTable == TypesTable.SYSTOLIC }
            if (filter.isNotEmpty()) {
                getDoubleListFromStringMap(filter[0].data)
            } else {
                MutableList(48) { 0.0 }.toList()
            }

        } else {
            MutableList(48) { 0.0 }.toList()
        }

        val diastolicList = if (dataCoroutineFromDB.isEmpty().not()) {
            val filter = dataCoroutineFromDB.filter { it.typesTable == TypesTable.DIASTOLIC }
            if (filter.isNotEmpty()) {
                getDoubleListFromStringMap(filter[0].data)
            } else {
                MutableList(48) { 0.0 }.toList()
            }
        } else {
            MutableList(48) { 0.0 }.toList()
        }


        doubleFieldUpdateOrInsert(
            dataFieldFromSW = values.systolicList,
            dataFieldFromDB = dataCoroutineFromDB,
            fieldListState = systolicList,
            dataViewModel = dataViewModel,
            typesTableToModify = TypesTable.SYSTOLIC,
            dateData = queryDate,
        )

        doubleFieldUpdateOrInsert(
            dataFieldFromSW = values.diastolicList,
            dataFieldFromDB = dataCoroutineFromDB,
            fieldListState = diastolicList,
            dataViewModel = dataViewModel,
            typesTableToModify = TypesTable.DIASTOLIC,
            dateData = queryDate,
        )


    }

}


fun updateOrInsertHeartRateDataBase(
    values: Values,
    queryDate: String,
    queryMacAddress: String,
    dataViewModel: DataViewModel,
    dbRepository: DBRepository
) {
    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {

        val dataDeferred = async {
            dbRepository.getDayHeartRateWithCoroutine(queryDate, queryMacAddress)
        }

        var dataCoroutineFromDB = dataDeferred.await()

        dataCoroutineFromDB = dataCoroutineFromDB.ifEmpty {
            notFoundHeartRateList(queryDate, queryMacAddress)
        }

        val heartRateList = if (dataCoroutineFromDB.isEmpty().not()) {
            val filter = dataCoroutineFromDB.filter { it.typesTable == TypesTable.HEART_RATE }
            if (filter.isNotEmpty()) {
                getDoubleListFromStringMap(filter[0].data)
            } else {
                MutableList(48) { 0.0 }.toList()
            }

        } else {
            MutableList(48) { 0.0 }.toList()
        }


        doubleFieldUpdateOrInsert(
            dataFieldFromSW = values.heartRateList,
            dataFieldFromDB = dataCoroutineFromDB,
            fieldListState = heartRateList,
            dataViewModel = dataViewModel,
            typesTableToModify = TypesTable.HEART_RATE,
            dateData = queryDate,
        )



    }

}







