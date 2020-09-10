package com.matej.roadsurfacetopography.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.matej.roadsurfacetopography.model.SensorDataDb

@Database(entities = [SensorDataDb::class], version = 1)
abstract class SensorDataDaoProvider: RoomDatabase() {
    abstract fun favouritesDao() : SensorDataDao
}