package com.matej.roadsurfacetopography.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.matej.roadsurfacetopography.model.SensorDataDb

@Dao
interface SensorDataDao {
    @Insert
    fun insertSensorData(sensorData: SensorDataDb)

    @Query("SELECT * FROM sensorData ORDER BY id DESC") //dodat WHERE user = :user
    fun getAllSensorData(): List<SensorDataDb> //dodat (user: String)

}