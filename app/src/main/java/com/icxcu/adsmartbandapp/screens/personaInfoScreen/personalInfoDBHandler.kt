package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun PersonalInfoDBHandler(
    dataViewModel: DataViewModel,
) {

    val getPersonalInfoDataState = {
        dataViewModel.personalInfoDataState
    }
    //Data Sources
    val personalInfoResultsFromDB by dataViewModel.personalInfoFromDB.observeAsState(
        MutableList(0) { PersonalInfo(
            id = -1,
            macAddress = "",
            typesTable= TypesTable.PERSONAL_INFO,
            name = "",
            birthdate = "",
            weight = 0.0,
            height = 0.0 ) }.toList()
    )

    dataViewModel.personalInfoListReadFromDB = if (personalInfoResultsFromDB.isEmpty().not() && personalInfoResultsFromDB[0].id!=-1) {
        val filter = personalInfoResultsFromDB.filter { it.typesTable == TypesTable.PERSONAL_INFO }
        getPersonalInfoDataState().name = filter[0].name
        getPersonalInfoDataState().date = filter[0].birthdate
        getPersonalInfoDataState().weight = filter[0].weight.toString()
        getPersonalInfoDataState().height = filter[0].height.toString()
        filter
    } else {
        mutableListOf()
    }



}