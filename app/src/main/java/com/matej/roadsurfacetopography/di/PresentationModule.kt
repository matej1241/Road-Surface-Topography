package com.matej.roadsurfacetopography.di

import com.matej.roadsurfacetopography.presentation.LoginPresenter
import com.matej.roadsurfacetopography.presentation.RegistrationPresenter
import com.matej.roadsurfacetopography.ui.authentication.login.LoginContract
import com.matej.roadsurfacetopography.ui.authentication.registration.RegistrationContract
import org.koin.dsl.module

val presentationModule = module {
    factory<RegistrationContract.Presenter> { RegistrationPresenter(get()) }
    factory<LoginContract.Presenter> { LoginPresenter(get()) }
}