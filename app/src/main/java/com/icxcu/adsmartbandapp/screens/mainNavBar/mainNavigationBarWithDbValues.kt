package com.icxcu.adsmartbandapp.screens.mainNavBar


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.repositories.BloodPressureData
import com.icxcu.adsmartbandapp.repositories.TemperatureData
import com.icxcu.adsmartbandapp.repositories.Values
import com.icxcu.adsmartbandapp.screens.BluetoothListScreenNavigationStatus
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



