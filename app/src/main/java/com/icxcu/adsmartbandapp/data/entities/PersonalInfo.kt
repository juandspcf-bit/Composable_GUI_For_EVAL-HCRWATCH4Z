package com.icxcu.adsmartbandapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.icxcu.adsmartbandapp.data.TypesTable

@Entity(tableName = "PersonalInfo")
class PersonalInfo {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "personalInfoId")
    var id: Int = 0

    @ColumnInfo(name = "mac_address")
    var macAddress: String = ""

    @ColumnInfo(name = "types_table")
    var typesTable: TypesTable = TypesTable.PERSONAL_INFO

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "birthdate")
    var birthdate: String = ""

    @ColumnInfo(name = "weight")
    var weight: Double = 0.0

    @ColumnInfo(name = "height")
    var height: Double = 0.0

    constructor()
    constructor(
        id: Int,
        macAddress: String,
        typesTable: TypesTable,
        name: String,
        birthdate: String,
        weight: Double,
        height: Double
    ) {
        this.id = id
        this.macAddress = macAddress
        this.typesTable = typesTable
        this.name = name
        this.birthdate = birthdate
        this.weight = weight
        this.height = height
    }

    override fun toString(): String {
        return "PersonalInfo(id=$id, macAddress='$macAddress', typesTable=$typesTable, name='$name', birthdate='$birthdate', weight=$weight, height=$height)"
    }



}