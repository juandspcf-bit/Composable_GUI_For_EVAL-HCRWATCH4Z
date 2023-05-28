package com.icxcu.adsmartbandapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {
    private val _stateFlow = MutableStateFlow(false)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        startDelay()
    }

private fun startDelay(){
    viewModelScope.launch {
        delay(2000)
        _stateFlow.value = true
    }
}

}