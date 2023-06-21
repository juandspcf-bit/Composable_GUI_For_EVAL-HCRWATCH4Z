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




    @Query("SELECT * FROM PersonalInfo")
    suspend fun getPersonalInfoWithCoroutine(): List<PersonalInfo>


    @Insert
    suspend fun insertPersonalInfoDataWithCoroutine(personalInfo: PersonalInfo)

    @Update
    suspend fun updatePersonalInfoDataWithCoroutine(personalInfo: PersonalInfo)
}