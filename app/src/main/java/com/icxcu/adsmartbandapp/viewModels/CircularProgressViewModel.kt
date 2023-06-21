package com.icxcu.adsmartbandapp.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo
import com.icxcu.adsmartbandapp.database.SWRoomDatabase
import com.icxcu.adsmartbandapp.repositories.DBRepository
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.PersonalInfoDataScreenNavStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CircularProgressViewModel(var application: Application) : ViewModel(){
    private var dbRepository: DBRepository

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


    var personalInfoDataStateC by mutableStateOf<List<PersonalInfo>>(listOf())
    var jobPersonalInfoDataState: Job? = null
    var personalInfoDataScreenNavStatus: PersonalInfoDataScreenNavStatus = PersonalInfoDataScreenNavStatus.Leaving

    fun starListeningPersonalInfoDB(macAddress: String = "") {
        jobPersonalInfoDataState = viewModelScope.launch {

            val dataDeferred = async {
                dbRepository.getPersonalInfoWithCoroutine()
            }

            val dataCoroutineFromDB = dataDeferred.await()

            personalInfoDataStateC = dataCoroutineFromDB.ifEmpty {
                MutableList(1) { PersonalInfo(
                    id = -1,
                    typesTable= TypesTable.PERSONAL_INFO,
                    name = "",
                    birthdate = "",
                    weight = 0.0,
                    height = 0.0 ) }.toList()
            }
        }
    }
}