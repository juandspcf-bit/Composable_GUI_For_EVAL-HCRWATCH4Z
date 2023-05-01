package com.icxcu.adsmartbandapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.icxcu.adsmartbandapp.data.entities.HeartRate

@Dao
interface HeartRateDao {
    @Insert
    fun insertHeartRateData(heartRate: HeartRate)
    @Query("SELECT * FROM HeartRate WHERE date_data = :date AND mac_address=:macAddress LIMIT 3")
    fun getThreeLatestDaysHeartRateData(date: String, macAddress: String): List<HeartRate>

    @Query("SELECT * FROM HeartRate WHERE date_data = :date AND mac_address=:macAddress LIMIT 3")
    fun getDayHeartRateData(date: String, macAddress: String): List<HeartRate>

    @Query("SELECT * FROM HeartRate")
    fun getThreeLatestDaysHeartRateFlow(): List<HeartRate>

    @Update
    fun updateHeartRateData(heartRate: HeartRate)

}