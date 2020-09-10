package com.matej.roadsurfacetopography.di

import com.matej.roadsurfacetopography.presentation.DataMonitorPresenter
import com.matej.roadsurfacetopography.presentation.LoginPresenter
import com.matej.roadsurfacetopography.presentation.RegistrationPresenter
import com.matej.roadsurfacetopography.ui.authentication.login.LoginContract
import com.matej.roadsurfacetopography.ui.authentication.registration.RegistrationContract
import com.matej.roadsurfacetopography.ui.homePage.dataMonitor.DataMonitorContract
import org.koin.dsl.module

val presentationModule = module {
    factory<RegistrationContract.Presenter> { RegistrationPresenter(get()) }
    factory<LoginContract.Presenter> { LoginPresenter(get()) }
    factory<DataMonitorContract.Presenter> { DataMonitorPresenter(get()) }
}