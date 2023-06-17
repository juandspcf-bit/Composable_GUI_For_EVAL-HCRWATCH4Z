package com.icxcu.adsmartbandapp.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HeartRateViewModelFactory (var application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HeartRateViewModel(application = application) as T
    }
}