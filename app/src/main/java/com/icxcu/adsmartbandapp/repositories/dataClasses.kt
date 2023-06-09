package com.icxcu.adsmartbandapp.repositories

import com.icxcu.adsmartbandapp.screens.mainNavBar.StatusReadingDbForDashboard

data class BloodPressureData(val systolic:Int, val diastolic:Int)

data class TemperatureData(val body:Double, val skin:Double)


data class DayHealthResultsFromDBFForDashBoard(val values: Values, val statusReadingDbForDashboard: StatusReadingDbForDashboard)