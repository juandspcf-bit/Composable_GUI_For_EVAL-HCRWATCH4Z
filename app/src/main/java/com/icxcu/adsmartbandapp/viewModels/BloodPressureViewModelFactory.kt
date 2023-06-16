package com.icxcu.adsmartbandapp.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BloodPressureViewModelFactory (var application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BloodPressureViewModel(application = application) as T
    }
}