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
import java.text.SimpleDateFormat
import java.util.*

class DataViewModel(var application: Application) : ViewModel() {
    var values by mutableStateOf(Values(MutableList(48){0}.toList(),
        MutableList(48){0.0}.toList(),
        MutableList(48){0.0}.toList(),
        MutableList(48){ listOf(0.0, 0.0) }.toList()))
    private var swRepository: SWRepository
    var searchResults = MutableLiveData<List<PhysicalActivity>>()
    var dayPhysicalActivityData = MutableLiveData<List<PhysicalActivity>>()
    var macAddress:String=""
    var name:String=""

    init {
        val swDb = SWRoomDatabase.getInstance(application)
        val physicalActivityDao = swDb.physicalActivityDao()
        swRepository = SWRepository(physicalActivityDao)
        searchResults = swRepository.searchResults
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