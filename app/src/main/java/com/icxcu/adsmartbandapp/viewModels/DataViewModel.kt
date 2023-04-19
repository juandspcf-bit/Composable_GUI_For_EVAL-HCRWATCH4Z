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
    var myDateObj = LocalDateTime.now()
    var myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    var formattedDate = myDateObj.format(myFormatObj)
    var todayDateValues by mutableStateOf(Values(MutableList(48){0}.toList(),
        MutableList(48){0.0}.toList(),
        MutableList(48){0.0}.toList(),
        MutableList(48){ listOf(0.0, 0.0) }.toList(), formattedDate) )

    var todayPhysicalActivityResults = MutableLiveData<List<PhysicalActivity>>()
    var todayStepList by mutableStateOf(listOf<Int>())
    var todayStepsAlreadyInserted by mutableStateOf(false)
    var todayStepsAlreadyUpdated by mutableStateOf(false)

    var todayDistanceList by mutableStateOf(listOf<Double>())
    var todayDistanceAlreadyInserted by mutableStateOf(false)
    var todayDistanceAlreadyUpdated by mutableStateOf(false)

    var todayCaloriesList by mutableStateOf(listOf<Double>())
    var todayCaloriesAlreadyInserted by mutableStateOf(false)
    var todayCaloriesAlreadyUpdated by mutableStateOf(false)

    var yesterdayDate = LocalDateTime.now().minusDays(1)
    var yesterdayFormattedDate = yesterdayDate.format(myFormatObj)
    var yesterdayDateValues by mutableStateOf(Values(MutableList(48){0}.toList(),
        MutableList(48){0.0}.toList(),
        MutableList(48){0.0}.toList(),
        MutableList(48){ listOf(0.0, 0.0) }.toList(), yesterdayFormattedDate) )



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
        todayPhysicalActivityResults = swRepository.todayPhysicalActivityResults


        viewModelScope.launch{
            swRepository.sharedStepsFlow.collect{
                val myDateObj = LocalDateTime.now()
                val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val formattedDate = myDateObj.format(myFormatObj)
                if(formattedDate==it.date){
                    todayDateValues = it
                }

            }
        }

        swRepository.requestStepsData()
    }


    fun insertPhysicalActivityData(physicalActivity: PhysicalActivity) {
        swRepository.insertPhysicalActivityData(physicalActivity)
    }


    fun getTodayPhysicalActivityData(dateData:String, macAddress:String) {
        swRepository.getTodayPhysicalActivityData(dateData, macAddress)
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