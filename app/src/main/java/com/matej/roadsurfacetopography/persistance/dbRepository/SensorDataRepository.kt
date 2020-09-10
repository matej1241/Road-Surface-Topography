package com.matej.roadsurfacetopography.persistance.dbRepository

import com.matej.roadsurfacetopography.model.SensorDataDb

interface SensorDataRepository {

    fun insertSensorData(sensorData: SensorDataDb)

    fun getAllNews(): List<SensorDataDb>
}