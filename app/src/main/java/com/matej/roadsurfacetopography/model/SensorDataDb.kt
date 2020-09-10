package com.matej.roadsurfacetopography.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sensorData")
data class SensorDataDb (
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "user") val user: String = "",
    @ColumnInfo(name = "sensor_value") val sensorValue: Int,
    @ColumnInfo(name = "locationX") val locationX: String = "",
    @ColumnInfo(name = "LocationY") val locationY: String = ""
)