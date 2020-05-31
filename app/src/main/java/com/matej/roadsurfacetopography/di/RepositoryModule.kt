package com.matej.roadsurfacetopography.di

import com.matej.roadsurfacetopography.persistance.firebaseRepository.FirebaseRepository
import com.matej.roadsurfacetopography.persistance.firebaseRepository.FirebaseRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<FirebaseRepository> { FirebaseRepositoryImpl(get()) }
}