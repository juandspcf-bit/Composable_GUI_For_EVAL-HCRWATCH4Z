package com.icxcu.adsmartbandapp.viewModels

import android.app.Application
import android.util.Log
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
import java.text.SimpleDateFormat
import java.util.*

class DataViewModel(var application: Application) : ViewModel() {
    var values by mutableStateOf(Values(MutableList(48){0}.toList(),
        MutableList(48){0.0}.toList(),
        MutableList(48){0.0}.toList(),
        MutableList(48){ listOf(0.0, 0.0) }.toList()))
    var stepList by mutableStateOf(listOf<Int>())
    var stepsAlreadyInserted by mutableStateOf(false)
    var stepsAlreadyUpdated by mutableStateOf(false)

    var distanceList by mutableStateOf(listOf<Double>())
    var distanceAlreadyInserted by mutableStateOf(false)
    var distanceAlreadyUpdated by mutableStateOf(false)

    var caloriesList by mutableStateOf(listOf<Double>())
    var caloriesAlreadyInserted by mutableStateOf(false)
    var caloriesAlreadyUpdated by mutableStateOf(false)

    private var swRepository: SWRepository
    var todayPhysicalActivityResults = MutableLiveData<List<PhysicalActivity>>()
    var dayPhysicalActivityData = MutableLiveData<List<PhysicalActivity>>()
    var macAddress:String=""
    var name:String=""

    //Tabs Selector for physical activity plots
    var stateShowDialogDatePicker by mutableStateOf(false)

    init {
        val swDb = SWRoomDatabase.getInstance(application)
        val physicalActivityDao = swDb.physicalActivityDao()
        swRepository = SWRepository(physicalActivityDao)
        todayPhysicalActivityResults = swRepository.todayPhysicalActivityResults
        dayPhysicalActivityData= swRepository.dayPhysicalActivityData

        viewModelScope.launch{
            swRepository.sharedStepsFlow.collect{
                values = it
            }
        }

        swRepository.requestStepsData()
    }


    fun insertPhysicalActivityData(physicalActivity: PhysicalActivity) {
        swRepository.insertPhysicalActivityData(physicalActivity)
    }

    private fun getThreeLatestDaysPhysicalActivityData(date: String,
                                                       macAddress:String) {
        swRepository.getThreeLatestDaysPhysicalActivityData(date, macAddress)
    }

    fun getDayPhysicalActivityData(date: String, macAddress:String) {
        swRepository.getDayPhysicalActivityData(date, macAddress)
    }

    fun getToDayPhysicalActivityData( macAddress:String) {
        val date = Date()
        val formattedDate =  SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateData= formattedDate.format(date)
        Log.d("Date", "getToDayPhysicalActivityData: $dateData")
        swRepository.getToDayPhysicalActivityData(dateData, macAddress)
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