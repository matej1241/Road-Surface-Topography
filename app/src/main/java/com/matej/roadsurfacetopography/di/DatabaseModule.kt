package com.matej.roadsurfacetopography.di

import androidx.room.Room
import com.matej.roadsurfacetopography.RoadSurfaceTopography
import com.matej.roadsurfacetopography.db.SensorDataDaoProvider
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            RoadSurfaceTopography.instance,
            SensorDataDaoProvider::class.java,
            "favorites_database")
            .allowMainThreadQueries().build() }

    single { get<SensorDataDaoProvider>().favouritesDao() }
}