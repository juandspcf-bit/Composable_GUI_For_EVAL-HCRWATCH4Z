package com.icxcu.adsmartbandapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.icxcu.adsmartbandapp.data.TypesTable

interface Field {
    var id: Int

    var macAddress: String

    var typesTable: TypesTable

    var dateData: String

    var data: String
}