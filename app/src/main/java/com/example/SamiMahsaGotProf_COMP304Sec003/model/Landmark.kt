package com.example.SamiMahsaGotProf_COMP304Sec003.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_landmark")
data class Landmark(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val place_name: String,
    @ColumnInfo(name = "Address")
    val place_address: String,
    @ColumnInfo(name = "type")
    val place_type: LandmarkType,
    @ColumnInfo(name = "latitude")
    val place_latitude: Double,
    @ColumnInfo(name = "longitude")
    val place_longitude: Double
)
