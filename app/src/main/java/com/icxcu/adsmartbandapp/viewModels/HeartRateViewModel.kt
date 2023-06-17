package com.icxcu.adsmartbandapp.viewModels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.database.SWRoomDatabase
import com.icxcu.adsmartbandapp.repositories.DBRepository
import com.icxcu.adsmartbandapp.screens.plotsFields.heartRate.HeartRateScreenNavStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HeartRateViewModel (var application: Application) : ViewModel() {
    var selectedDay by mutableStateOf("")

    private var dbRepository: DBRepository

    var stateShowDialogDatePicker by mutableStateOf(false)
    var stateMiliSecondsDateDialogDatePicker by mutableStateOf(0L)

    var dayHeartRateState by mutableStateOf<List<HeartRate>>(listOf())
    var jobHeartRateState: Job? = null
    var heartRateScreenNavStatus: HeartRateScreenNavStatus =
        HeartRateScreenNavStatus.Leaving

    var dayHeartRateListFromDB by mutableStateOf(listOf<Double>())


    init {
        val swDb = SWRoomDatabase.getInstance(application)
        val physicalActivityDao = swDb.physicalActivityDao()
        val bloodPressureDao = swDb.bloodPressureDao()
        val heartRateDao = swDb.heartRateDao()
        val personalInfoDao = swDb.personalInfoDao()
        dbRepository = DBRepository(
            physicalActivityDao,
            bloodPressureDao,
            heartRateDao,
            personalInfoDao
        )
    }

    fun starListeningHeartRateDB(dateData: String = "", macAddress: String = "") {
        jobHeartRateState = viewModelScope.launch {
            dbRepository.getDayHeartRateWithFlow(dateData, macAddress)
                .distinctUntilChanged()
                .collect {
                    Log.d("DB_FLOW", "starListeningDB of $macAddress: $it")
                    dayHeartRateState = it
                }
        }
    }
}