package com.icxcu.adsmartbandapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icxcu.adsmartbandapp.repositories.SWRepository
import com.icxcu.adsmartbandapp.screens.mainNavBar.SWReadingStatus
import com.icxcu.adsmartbandapp.screens.mainNavBar.SmartWatchState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SplashViewModel: ViewModel() {


    val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val todayLocalDateTime = LocalDateTime.now()
    val todayFormattedDate = todayLocalDateTime.format(myFormatObj)
    val yesterdayLocalDateTime = todayLocalDateTime.minusDays(1)
    val yesterdayFormattedDate = yesterdayLocalDateTime.format(myFormatObj)
    val pastYesterdayLocalDateTime = todayLocalDateTime.minusDays(2)
    val pastYesterdayFormattedDate = pastYesterdayLocalDateTime.format(myFormatObj)

    val smartWatchState = SmartWatchState(todayFormattedDate, yesterdayFormattedDate)
    private var swRepository: SWRepository = SWRepository()

    init {

    }


    private val _stateFlow = MutableStateFlow(false)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        startDelay()
        
    }

private fun startDelay(){
    viewModelScope.launch {
        delay(2000)
        _stateFlow.value = true

        swRepository.sharedStepsFlow.collect {

            when (it.date) {
                todayFormattedDate -> {
                    smartWatchState.todayDateValuesReadFromSW = it
                }

                yesterdayFormattedDate -> {
                    smartWatchState.yesterdayDateValuesFromSW = it
                    smartWatchState.progressbarForFetchingDataFromSW = false
                    smartWatchState.fetchingDataFromSWStatus = SWReadingStatus.READ
                }
            }


        }
    }
}

    fun requestSmartWatchData(name: String = "", macAddress: String = "") {

        swRepository.requestSmartWatchData()

        viewModelScope.launch {
            delay(1000)
            smartWatchState.progressbarForFetchingDataFromSW = true
        }

    }

}