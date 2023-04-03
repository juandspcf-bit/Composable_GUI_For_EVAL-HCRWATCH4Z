package com.icxcu.adsmartbandapp.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icxcu.adsmartbandapp.repositories.MockRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataViewModel(var application: Application) : ViewModel() {
    var dataSteps by mutableStateOf(listOf<Int>())
    private var mockRepository: MockRepository = MockRepository()


    init {
        viewModelScope.launch{
            mockRepository.sharedStepsFlow.collect{
                dataSteps = it
            }
        }

        mockRepository.requestStepsData()
    }

}