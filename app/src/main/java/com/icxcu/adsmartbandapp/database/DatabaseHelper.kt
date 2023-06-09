package com.icxcu.adsmartbandapp.database

import android.util.Log
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface DatabaseHelper {
    fun getUsers(date: String, macAddress: String): Flow<List<PhysicalActivity>>

}

class DatabaseHelperImpl(private val swRoomDatabase: SWRoomDatabase) : DatabaseHelper {

    override fun getUsers(date: String, macAddress: String): Flow<List<PhysicalActivity>> = flow {
        Log.d("DB_FLOW", "$date, $macAddress")
        //emit(swRoomDatabase.physicalActivityDao().getAllPhysicalActivityFlow(date, macAddress))
    }

}