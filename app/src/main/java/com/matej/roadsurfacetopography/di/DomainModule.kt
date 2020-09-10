package com.matej.roadsurfacetopography.di

import com.matej.roadsurfacetopography.domain.authentication.LoginUseCase
import com.matej.roadsurfacetopography.domain.authentication.LoginUseCaseImpl
import com.matej.roadsurfacetopography.domain.authentication.RegistrationUseCase
import com.matej.roadsurfacetopography.domain.authentication.RegistrationUseCaseImpl
import com.matej.roadsurfacetopography.domain.database.SensorDataDbInteractor
import com.matej.roadsurfacetopography.domain.database.SensorDataDbInteractorImpl
import org.koin.dsl.module

val domainModule = module {
    factory<RegistrationUseCase> { RegistrationUseCaseImpl(get()) }
    factory<LoginUseCase> { LoginUseCaseImpl(get()) }
    factory<SensorDataDbInteractor> { SensorDataDbInteractorImpl(get()) }
}