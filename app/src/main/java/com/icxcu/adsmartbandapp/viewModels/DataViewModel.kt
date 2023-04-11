package com.icxcu.adsmartbandapp.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icxcu.adsmartbandapp.repositories.MockRepository
import com.icxcu.adsmartbandapp.repositories.Values
import kotlinx.coroutines.launch

class DataViewModel(var application: Application) : ViewModel() {
    var values by mutableStateOf(Values(MutableList(48){0}.toList(),
        MutableList(48){0.0}.toList(),
        MutableList(48){0.0}.toList(),
        MutableList(48){ listOf(0.0, 0.0) }.toList()))
    private var mockRepository: MockRepository = MockRepository()


    init {
        viewModelScope.launch{
            mockRepository.sharedStepsFlow.collect{
                values = it
            }
        }

        mockRepository.requestStepsData()
    }

}