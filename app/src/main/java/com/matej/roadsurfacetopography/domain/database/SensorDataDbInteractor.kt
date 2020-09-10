package com.matej.roadsurfacetopography.domain.database

import com.matej.roadsurfacetopography.model.SensorDataDb

interface SensorDataDbInteractor {
    fun insertSensorData(sensorData: SensorDataDb)

    fun getAllNews(): List<SensorDataDb>
}