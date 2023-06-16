package com.icxcu.adsmartbandapp.viewModels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.database.SWRoomDatabase
import com.icxcu.adsmartbandapp.repositories.DBRepository
import com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure.BloodPressureScreenNavStatus
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.PhysicalActivityScreenNavStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class BloodPressureViewModel (var application: Application) : ViewModel() {

    var selectedDay by mutableStateOf("")

    private var dbRepository: DBRepository

    var stateShowDialogDatePicker by mutableStateOf(false)
    var stateMiliSecondsDateDialogDatePicker by mutableStateOf(0L)

    var dayBloodPressureState by mutableStateOf<List<BloodPressure>>(listOf())
    var jobBloodPressureState: Job? = null
    var bloodPressureScreenNavStatus: BloodPressureScreenNavStatus =
        BloodPressureScreenNavStatus.Leaving

    var daySystolicListFromDB by mutableStateOf(listOf<Double>())
    var dayDiastolicListFromDB by mutableStateOf(listOf<Double>())

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

    fun starListeningBloodPressureDB(dateData: String = "", macAddress: String = "") {
        jobBloodPressureState = viewModelScope.launch {
            dbRepository.getDayBloodPressureWithFlow(dateData, macAddress)
                .distinctUntilChanged()
                .collect {
                    Log.d("DB_FLOW", "starListeningDB of $macAddress: $it")
                    dayBloodPressureState = it
                }
        }
    }

}