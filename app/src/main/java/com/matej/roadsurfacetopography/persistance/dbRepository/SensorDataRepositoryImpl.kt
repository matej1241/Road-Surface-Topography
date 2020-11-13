package com.matej.roadsurfacetopography.persistance.dbRepository

import com.matej.roadsurfacetopography.db.SensorDataDao
import com.matej.roadsurfacetopography.model.SensorDataDb

class SensorDataRepositoryImpl(private val sensorDataDao: SensorDataDao): SensorDataRepository {

    override fun insertSensorData(sensorData: SensorDataDb) = sensorDataDao.insertSensorData(sensorData)

    override fun getAllSensorData(user: String): List<SensorDataDb> = sensorDataDao.getAllSensorData(user)

    override fun removeSensorData(id: Long) = sensorDataDao.removeSensorData(id)

    override fun removeAllSensorData() = sensorDataDao.removeAllSensorData()
}
