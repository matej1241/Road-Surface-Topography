package com.matej.roadsurfacetopography.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.matej.roadsurfacetopography.model.SensorDataDb

@Dao
interface SensorDataDao {
    @Insert
    fun insertSensorData(sensorData: SensorDataDb)

    @Query("SELECT * FROM sensorData WHERE user = :user ORDER BY id DESC ")
    fun getAllSensorData(user: String): List<SensorDataDb>

    @Query("DELETE FROM sensorData WHERE id = :id")
    fun removeSensorData(id: Long)

    @Query("DELETE FROM sensorData")
    fun removeAllSensorData()
}