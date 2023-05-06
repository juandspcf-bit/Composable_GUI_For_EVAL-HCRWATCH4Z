package com.icxcu.adsmartbandapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [PhysicalActivity::class, BloodPressure::class, HeartRate::class, PersonalInfo::class], version = 1)
abstract class  SWRoomDatabase : RoomDatabase() {
    abstract fun physicalActivityDao(): PhysicalActivityDao
    abstract fun bloodPressureDao(): BloodPressureDao
    abstract fun heartRateDao(): HeartRateDao
    abstract fun personalInfoDao(): PersonalInfoDao

    companion object {
        private var INSTANCE: SWRoomDatabase? = null
        private val coroutineScope = CoroutineScope(Dispatchers.Main)
        fun getInstance(context: Context): SWRoomDatabase {
            synchronized(this) {

                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SWRoomDatabase::class.java,
                        "product_database"
                    )
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                Log.d("Database", "onCreate: ")
                                coroutineScope.launch(Dispatchers.IO){
                                    db.query("INSERT INTO PhysicalActivity (mac_address, date_data, data)\n" +
                                            "VALUES ('00:00:00', '2023/11/10', '{}'); ")
                                }


                            }

                        })
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance

            }
        }
    }
}