package com.icxcu.adsmartbandapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo

@Dao
interface PersonalInfoDao {
    @Insert
    fun insertPersonalInfoData(personalInfo: PersonalInfo)

    @Query("SELECT * FROM BloodPressure WHERE mac_address=:macAddress")
    fun getPersonalInfo(macAddress: String): List<PersonalInfo>

    @Update
    fun updatePersonalInfoData(personalInfo: PersonalInfo)
}