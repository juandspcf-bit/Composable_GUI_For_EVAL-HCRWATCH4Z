package com.icxcu.adsmartbandapp.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.database.SWRoomDatabase
import com.icxcu.adsmartbandapp.repositories.SWRepository
import com.icxcu.adsmartbandapp.repositories.Values
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DataViewModel(var application: Application) : ViewModel() {
    val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val todayLocalDateTime = LocalDateTime.now()
    val todayFormattedDate = todayLocalDateTime.format(myFormatObj)
    val yesterdayLocalDateTime = todayLocalDateTime.minusDays(1)
    val yesterdayFormattedDate = yesterdayLocalDateTime.format(myFormatObj)
    val pastYesterdayLocalDateTime = todayLocalDateTime.minusDays(2)
    val pastYesterdayFormattedDate = pastYesterdayLocalDateTime.format(myFormatObj)


    var dayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()
    var dayStepListFromDB by mutableStateOf(listOf<Int>())
    var dayDistanceListFromDB by mutableStateOf(listOf<Double>())
    var dayCaloriesListFromDB by mutableStateOf(listOf<Double>())

    var todayDateValuesReadFromSW by mutableStateOf(Values(MutableList(48){0}.toList(),
        MutableList(48){0.0}.toList(),
        MutableList(48){0.0}.toList(),
        MutableList(48){ listOf(0.0, 0.0) }.toList(), todayFormattedDate) )
    var todayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()
    var todayStepListReadFromDB by mutableStateOf(listOf<Int>())
    var isTodayStepsListAlreadyInsertedInDB by mutableStateOf(false)
    var isTodayStepsListInDBAlreadyUpdated by mutableStateOf(false)

    var todayDistanceListReadFromDB by mutableStateOf(listOf<Double>())
    var isTodayDistanceListAlreadyInsertedInDB by mutableStateOf(false)
    var isTodayDistanceListInDBAlreadyUpdated by mutableStateOf(false)

    var todayCaloriesListReadFromDB by mutableStateOf(listOf<Double>())
    var isTodayCaloriesListAlreadyInsertedInDB by mutableStateOf(false)
    var isTodayCaloriesListInDBAlreadyUpdated by mutableStateOf(false)




    var yesterdayDateValuesFromSW by mutableStateOf(Values(MutableList(48){0}.toList(),
        MutableList(48){0.0}.toList(),
        MutableList(48){0.0}.toList(),
        MutableList(48){ listOf(0.0, 0.0) }.toList(), yesterdayFormattedDate) )
    var yesterdayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()
    var yesterdayStepListReadFromDB by mutableStateOf(listOf<Int>())
    var isYesterdayStepsListAlreadyInsertedInDB by mutableStateOf(false)
    var isYesterdayStepsListInDBAlreadyUpdated by mutableStateOf(false)

    var yesterdayDistanceListReadFromDB by mutableStateOf(listOf<Double>())
    var isYesterdayDistanceListAlreadyInsertedInDB by mutableStateOf(false)
    var isYesterdayDistanceListInDBAlreadyUpdated by mutableStateOf(false)

    var yesterdayCaloriesListReadFromDB by mutableStateOf(listOf<Double>())
    var isYesterdayCaloriesListAlreadyInsertedInDB by mutableStateOf(false)
    var isYesterdayCaloriesListInDBAlreadyUpdated by mutableStateOf(false)


    private var swRepository: SWRepository


    var macAddress:String=""
    var name:String=""

    //DatePicker for physical activity plots
    var stateShowDialogDatePicker by mutableStateOf(false)
    var stateMiliSecondsDateDialogDatePicker by mutableStateOf(0L)

    init {
        val swDb = SWRoomDatabase.getInstance(application)
        val physicalActivityDao = swDb.physicalActivityDao()
        swRepository = SWRepository(physicalActivityDao)
        dayPhysicalActivityResultsFromDB = swRepository.dayPhysicalActivityResultsFromDB
        todayPhysicalActivityResultsFromDB = swRepository.todayPhysicalActivityResultsFromDB

        viewModelScope.launch{
            swRepository.sharedStepsFlow.collect{

                when(it.date){
                    todayFormattedDate -> todayDateValuesReadFromSW = it
                    yesterdayFormattedDate -> yesterdayDateValuesFromSW = it
                }


            }
        }

        swRepository.requestStepsData()
    }


    fun insertPhysicalActivityData(physicalActivity: PhysicalActivity) {
        swRepository.insertPhysicalActivityData(physicalActivity)
    }

    fun getDayPhysicalActivityData(dateData:String, macAddress:String) {
        swRepository.getDayPhysicalActivityData(dateData, macAddress)
    }

    fun getTodayPhysicalActivityData(macAddress:String) {
        swRepository.getTodayPhysicalActivityData(todayFormattedDate, macAddress)
    }
    fun getYesterdayPhysicalActivityData(macAddress:String) {
        swRepository.getYesterdayPhysicalActivityData(yesterdayFormattedDate, macAddress)
    }

    fun updatePhysicalActivityData(physicalActivity: PhysicalActivity){
        swRepository.updatePhysicalActivityData(physicalActivity)
    }


}

fun randomData():String{
    var fakeData = MutableList(48){
        (1..10000).random()
    }
    var values = mutableMapOf<String,String>()

    fakeData.forEachIndexed{ index, i ->
        values[index.toString()]= i.toString()
    }

    return values.toString()

}