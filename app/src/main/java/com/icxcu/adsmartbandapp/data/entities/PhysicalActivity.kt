package com.icxcu.adsmartbandapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.icxcu.adsmartbandapp.data.TypesTable

@Entity(tableName = "PhysicalActivity")
class PhysicalActivity : Field{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "physicalActivityId")
    override var id: Int = 0

    @ColumnInfo(name = "mac_address")
    override var macAddress: String = ""

    @ColumnInfo(name = "types_table")
    override var typesTable: TypesTable = TypesTable.STEPS

    @ColumnInfo(name = "date_data")
    override var dateData: String = ""

    @ColumnInfo(name = "data")
    override var data: String = ""

    constructor() {}
    constructor(physicalActivityId: Int, macAddress: String, dateData: String, data:String) {
        this.id = physicalActivityId
        this.macAddress = macAddress
        this.dateData = dateData
        this.data = data
    }

    override fun toString(): String {
        return "PhysicalActivity(physicalActivityId=$id, macAddress='$macAddress', typesTable=$typesTable, dateData='$dateData', data='$data')"
    }


}