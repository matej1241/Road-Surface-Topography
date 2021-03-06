package com.matej.roadsurfacetopography.di

import com.matej.roadsurfacetopography.domain.firebase.CurrentUserUseCase
import com.matej.roadsurfacetopography.domain.firebase.CurrentUserUseCaseImpl
import com.matej.roadsurfacetopography.persistance.dbRepository.SensorDataRepository
import com.matej.roadsurfacetopography.persistance.dbRepository.SensorDataRepositoryImpl
import com.matej.roadsurfacetopography.persistance.firebaseRepository.FirebaseRepository
import com.matej.roadsurfacetopography.persistance.firebaseRepository.FirebaseRepositoryImpl
import com.matej.roadsurfacetopography.persistance.listenerRepository.ListenerRepository
import com.matej.roadsurfacetopography.persistance.listenerRepository.ListenerRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<FirebaseRepository> { FirebaseRepositoryImpl(get(), get()) }
    factory<SensorDataRepository> { SensorDataRepositoryImpl(get()) }
    factory<CurrentUserUseCase> { CurrentUserUseCaseImpl(get()) }
    factory<ListenerRepository> { ListenerRepositoryImpl() }
}