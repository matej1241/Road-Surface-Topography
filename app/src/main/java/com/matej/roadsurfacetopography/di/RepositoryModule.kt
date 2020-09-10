package com.matej.roadsurfacetopography.di

import com.matej.roadsurfacetopography.persistance.dbRepository.SensorDataRepository
import com.matej.roadsurfacetopography.persistance.dbRepository.SensorDataRepositoryImpl
import com.matej.roadsurfacetopography.persistance.firebaseRepository.FirebaseRepository
import com.matej.roadsurfacetopography.persistance.firebaseRepository.FirebaseRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<FirebaseRepository> { FirebaseRepositoryImpl(get()) }
    factory<SensorDataRepository> { SensorDataRepositoryImpl(get()) }
}