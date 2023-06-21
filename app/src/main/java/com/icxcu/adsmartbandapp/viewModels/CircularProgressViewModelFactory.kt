package com.icxcu.adsmartbandapp.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CircularProgressViewModelFactory (var application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CircularProgressViewModel(application = application) as T
    }
}