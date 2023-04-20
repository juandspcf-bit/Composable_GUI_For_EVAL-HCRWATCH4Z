package com.icxcu.adsmartbandapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.icxcu.adsmartbandapp.data.TypesTable

@Entity(tableName = "BloodPressure")
class BloodPressure {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bloodPressureId")
    var bloodPressureId: Int = 0

    @ColumnInfo(name = "mac_address")
    var macAddress: String = ""

    @ColumnInfo(name = "types_table")
    var typesTable: TypesTable = TypesTable.SYSTOLIC

    @ColumnInfo(name = "date_data")
    var dateData: String = ""

    @ColumnInfo(name = "data")
    var data: String = ""

    constructor() {}
    constructor(physicalActivityId: Int, macAddress: String, dateData: String, data:String) {
        this.bloodPressureId = physicalActivityId
        this.macAddress = macAddress
        this.dateData = dateData
        this.data = data
    }

    override fun toString(): String {
        return "BloodPressure(bloodPressureId=$bloodPressureId, macAddress='$macAddress', typesTable=$typesTable, dateData='$dateData', data='$data')"
    }


}