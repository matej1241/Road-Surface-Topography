package com.matej.roadsurfacetopography.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sensorData")
data class SensorDataDb (
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "user") val user: String = "",
    @ColumnInfo(name = "sensor_value") val sensorValue: Double = 0.00,
    @ColumnInfo(name = "locationX") val locationX: Double = 0.00,
    @ColumnInfo(name = "LocationY") val locationY: Double = 0.00,
    @ColumnInfo(name = "bump_type") val bumpType: Int = 0,
    @ColumnInfo(name = "fs_id") val fsId: String = ""
)