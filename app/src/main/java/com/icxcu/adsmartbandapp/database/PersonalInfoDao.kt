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


    @Query("SELECT * FROM PersonalInfo WHERE mac_address=:macAddress")
    fun getPersonalInfo(macAddress: String): List<PersonalInfo>

    @Query("SELECT * FROM PersonalInfo WHERE mac_address=:macAddress")
    suspend fun getPersonalInfoWithCoroutine(macAddress: String): List<PersonalInfo>

    @Update
    fun updatePersonalInfoData(personalInfo: PersonalInfo)

    @Update
    suspend fun updatePersonalInfoDataWithCoroutine(personalInfo: PersonalInfo)
}