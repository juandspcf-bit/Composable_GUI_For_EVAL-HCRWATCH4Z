package com.icxcu.adsmartbandapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity

@Dao
interface BloodPressureDao {
    @Insert
    fun insertBloodPressureData(bloodPressure: BloodPressure)
    @Query("SELECT * FROM BloodPressure WHERE date_data = :date AND mac_address=:macAddress LIMIT 3")
    fun getThreeLatestDaysBloodPressureData(date: String, macAddress: String): List<BloodPressure>

    @Query("SELECT * FROM BloodPressure WHERE date_data = :date AND mac_address=:macAddress LIMIT 3")
    fun getDayBloodPressureData(date: String, macAddress: String): List<BloodPressure>

    @Query("SELECT * FROM BloodPressure")
    fun getThreeLatestDaysBloodPressureFlow(): List<BloodPressure>

    @Update
    fun updateBloodPressureData(bloodPressure: BloodPressure)

    @Query("SELECT * FROM BloodPressure WHERE date_data = :date AND mac_address=:macAddress LIMIT 3")
    suspend fun getDayBloodPressureWithCoroutine(date: String, macAddress: String): List<BloodPressure>

}