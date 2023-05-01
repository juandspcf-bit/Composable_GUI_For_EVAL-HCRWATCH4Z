package com.icxcu.adsmartbandapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.icxcu.adsmartbandapp.data.TypesTable

@Entity(tableName = "HeartRate")
class HeartRate : Field {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "heartRateId")
    override var id: Int = 0

    @ColumnInfo(name = "mac_address")
    override var macAddress: String = ""

    @ColumnInfo(name = "types_table")
    override var typesTable: TypesTable = TypesTable.HEART_RATE

    @ColumnInfo(name = "date_data")
    override var dateData: String = ""

    @ColumnInfo(name = "data")
    override var data: String = ""

    constructor() {}
    constructor(heartRateId: Int, macAddress: String, dateData: String, data:String) {
        this.id = heartRateId
        this.macAddress = macAddress
        this.dateData = dateData
        this.data = data
    }

    override fun toString(): String {
        return "HeartRate(id=$id, macAddress='$macAddress', typesTable=$typesTable, dateData='$dateData', data='$data')"
    }


}