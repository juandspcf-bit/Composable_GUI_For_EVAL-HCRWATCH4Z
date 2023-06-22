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
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.InsertAlertDialogPersonalFieldVisibilityState
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.InvalidAlertDialogState
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.PersonalInfoDataScreenNavStatus
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.PersonalInfoDataState
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.UpdateAlertDialogPersonalFieldVisibilityState
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.ValidatorsPersonalField
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PersonalInfoViewModel (var application: Application) : ViewModel() {

    private var dbRepository: DBRepository

    var personalInfoDataState = PersonalInfoDataState()
    var invalidAlertDialogState = InvalidAlertDialogState()
    var updateAlertDialogPersonalFieldVisibilityState = UpdateAlertDialogPersonalFieldVisibilityState()
    var insertAlertDialogPersonalFieldVisibilityState = InsertAlertDialogPersonalFieldVisibilityState()

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

    var jobPersonalInfoDataState: Job? = null
    var personalInfoDataScreenNavStatus: PersonalInfoDataScreenNavStatus = PersonalInfoDataScreenNavStatus.Leaving

    fun starListeningPersonalInfoDB() {
        jobPersonalInfoDataState = viewModelScope.launch {

            val dataDeferred = async {
                dbRepository.getPersonalInfoWithCoroutine()
            }

            val dataCoroutineFromDB = dataDeferred.await()

            val personalInfoDataStateC = dataCoroutineFromDB.ifEmpty {
                MutableList(1) { PersonalInfo(
                    id = -1,
                    typesTable= TypesTable.PERSONAL_INFO,
                    name = "",
                    birthdate = "",
                    weight = 0.0,
                    height = 0.0 ) }.toList()
            }

            personalInfoDataState.apply {
                id = personalInfoDataStateC[0].id
                name = personalInfoDataStateC[0].name
                date = personalInfoDataStateC[0].birthdate
                height = personalInfoDataStateC[0].height.toString()
                weight = personalInfoDataStateC[0].weight.toString()
            }

        }
    }



    fun updatePersonalInfoDataWithCoroutine(personalInfo: PersonalInfo) {
        val dataDeferred = viewModelScope.async {
            dbRepository.updatePersonalInfoDataWithCoroutine(personalInfo = personalInfo)
        }

        viewModelScope.launch {
            updateAlertDialogPersonalFieldVisibilityState
                .updateAlertDialogPersonalFieldVisibility = dataDeferred.await()
            starListeningPersonalInfoDB()
        }
    }

    fun insertPersonalInfoDataWithCoroutine(personalInfo: PersonalInfo) {
        val dataDeferred = viewModelScope.async {
            dbRepository.insertPersonalInfoDataWithCoroutine(personalInfo)
        }

        viewModelScope.launch {
            insertAlertDialogPersonalFieldVisibilityState
                .insertAlertDialogPersonalFieldVisibility = dataDeferred.await()
            starListeningPersonalInfoDB()
        }


    }

    fun validatePersonalInfo(
        getPersonalInfoDataStateState: () -> PersonalInfoDataState,
    ): List<String> {
        val validationFields = mapOf(
            "Name" to getPersonalInfoDataStateState().name.isNotBlank(),
            "Birthday" to ValidatorsPersonalField.dateValidator(getPersonalInfoDataStateState().date)
                .isNotBlank(),
            "Weight" to ValidatorsPersonalField.weightValidator(getPersonalInfoDataStateState().weight)
                .isNotBlank(),
            "Height" to ValidatorsPersonalField.heightValidator(getPersonalInfoDataStateState().height)
                .isNotBlank()
        )

        val invalidFields = mutableListOf<String>()
        validationFields.forEach { (key, value) ->
            if (value.not()) {
                invalidFields.add(key)
            }
        }

        return invalidFields.toList()


    }


}