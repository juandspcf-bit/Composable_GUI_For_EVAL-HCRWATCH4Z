package com.icxcu.adsmartbandapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.icxcu.adsmartbandapp.data.TypesTable

@Entity(tableName = "ExtraEntity")
data class ExtraEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "physicalActivityId")
    val id: Int = 0,

    @ColumnInfo(name = "mac_address")
    val macAddress: String = "",

    @ColumnInfo(name = "types_table")
    val typesTable: TypesTable = TypesTable.STEPS,

    @ColumnInfo(name = "date_data")
    val dateData: String = "",

    @ColumnInfo(name = "data")
    var data: String = ""
)
