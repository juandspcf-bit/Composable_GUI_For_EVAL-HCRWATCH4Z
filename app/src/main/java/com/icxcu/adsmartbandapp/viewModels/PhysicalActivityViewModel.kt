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
import com.icxcu.adsmartbandapp.repositories.DBRepository
import com.icxcu.adsmartbandapp.screens.mainNavBar.DayHealthDataState
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.PhysicalActivityScreenNavStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class PhysicalActivityViewModel (var application: Application) : ViewModel() {

    var selectedDay by mutableStateOf("")

    private var dbRepository: DBRepository

    var stateShowDialogDatePicker by mutableStateOf(false)
    var stateMiliSecondsDateDialogDatePicker by mutableStateOf(0L)

    var dayHealthDataState = DayHealthDataState()

    var dayPhysicalActivityState by mutableStateOf<List<PhysicalActivity>>(listOf())
    var jobPhysicalActivityState: Job? = null
    var physicalActivityScreenNavStatus: PhysicalActivityScreenNavStatus = PhysicalActivityScreenNavStatus.Leaving

    var dayStepListFromDB by mutableStateOf(listOf<Int>())
    var dayDistanceListFromDB by mutableStateOf(listOf<Double>())
    var dayCaloriesListFromDB by mutableStateOf(listOf<Double>())

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



    fun starListeningDayPhysicalActivityDB(dateData: String="", macAddress: String = "", ) {
        jobPhysicalActivityState = viewModelScope.launch {
            dbRepository.getDayPhysicalActivityWithFlow(dateData, macAddress)
                .distinctUntilChanged()
                .collect {
                    Log.d("DB_FLOW", "starListeningDB of $macAddress: $it")
                    dayPhysicalActivityState = it
                }
        }
    }

}