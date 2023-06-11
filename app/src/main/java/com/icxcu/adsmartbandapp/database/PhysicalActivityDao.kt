package com.icxcu.adsmartbandapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhysicalActivityDao {
    @Insert
    fun insertPhysicalActivityData(physicalActivity: PhysicalActivity)
    @Query("SELECT * FROM PhysicalActivity WHERE date_data = :date AND mac_address=:macAddress LIMIT 3")
    fun getThreeLatestDaysPhysicalActivityData(date: String, macAddress: String): List<PhysicalActivity>

    @Query("SELECT * FROM PhysicalActivity WHERE date_data = :date AND mac_address=:macAddress LIMIT 3")
    fun getDayPhysicalActivityData(date: String, macAddress: String): List<PhysicalActivity>

    @Query("SELECT * FROM PhysicalActivity")
    fun getThreeLatestDaysPhysicalActivityFlow(): List<PhysicalActivity>

    @Update
    fun updatePhysicalActivityData(physicalActivity: PhysicalActivity)


    @Query("SELECT * FROM PhysicalActivity WHERE date_data = :date AND mac_address=:macAddress LIMIT 3")
    fun getDayPhysicalActivityWithFlow(date: String, macAddress: String): Flow<List<PhysicalActivity>>

    @Query("SELECT * FROM PhysicalActivity WHERE date_data = :date AND mac_address=:macAddress LIMIT 3")
    suspend fun getDayPhysicalActivityWithCoroutine(date: String, macAddress: String): List<PhysicalActivity>



}