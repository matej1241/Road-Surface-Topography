package com.matej.roadsurfacetopography.domain.database

import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.persistance.dbRepository.SensorDataRepository

class SensorDataDbInteractorImpl(private val sensorDataRepository: SensorDataRepository): SensorDataDbInteractor {
    override fun insertSensorData(sensorData: SensorDataDb) {
        sensorDataRepository.insertSensorData(sensorData)
    }

    override fun getAllSensorData(user: String): List<SensorDataDb> = sensorDataRepository.getAllSensorData(user)

    override fun removeSensorData(id: Long) = sensorDataRepository.removeSensorData(id)

}