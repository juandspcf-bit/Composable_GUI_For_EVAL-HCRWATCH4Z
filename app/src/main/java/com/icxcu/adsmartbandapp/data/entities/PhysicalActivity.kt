package com.icxcu.adsmartbandapp.data.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.icxcu.adsmartbandapp.data.TypesTable

@Entity(tableName = "PhysicalActivity")
class PhysicalActivity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "physicalActivityId")
    var physicalActivityId: Int = 0

    @ColumnInfo(name = "mac_address")
    var macAddress: String = ""

    @ColumnInfo(name = "types_table")
    var typesTable: TypesTable = TypesTable.STEPS

    @ColumnInfo(name = "date_data")
    var dateData: String = ""

    @ColumnInfo(name = "data")
    var data: String = ""

    constructor() {}
    constructor(physicalActivityId: Int, macAddress: String, dateData: String, data:String) {
        this.physicalActivityId = physicalActivityId
        this.macAddress = macAddress
        this.dateData = dateData
        this.data = data
    }

    override fun toString(): String {
        return "PhysicalActivity(physicalActivityId=$physicalActivityId, macAddress='$macAddress', typesTable=$typesTable, dateData='$dateData', data='$data')"
    }


}