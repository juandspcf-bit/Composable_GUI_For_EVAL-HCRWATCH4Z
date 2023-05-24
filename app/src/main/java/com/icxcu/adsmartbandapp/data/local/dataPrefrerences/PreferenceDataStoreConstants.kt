package com.icxcu.adsmartbandapp.data.local.dataPrefrerences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceDataStoreConstants {
    val HEIGHT_KEY = doublePreferencesKey("HEIGHT_KEY")
    val WEIGHT_KEY = doublePreferencesKey("WEIGHT_KEY")
    val LAST_DEVICE_KEY = stringPreferencesKey("LAST_DEVICE_KEY")
    val BIRTHDAY_KEY = stringPreferencesKey("BIRTHDAY_KEY")
}