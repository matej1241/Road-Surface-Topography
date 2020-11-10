package com.matej.roadsurfacetopography.di

import com.matej.roadsurfacetopography.presentation.*
import com.matej.roadsurfacetopography.ui.authentication.login.LoginContract
import com.matej.roadsurfacetopography.ui.authentication.registration.RegistrationContract
import com.matej.roadsurfacetopography.ui.homePage.dataList.DataListContract
import com.matej.roadsurfacetopography.ui.homePage.dataList.DataListFsContract
import com.matej.roadsurfacetopography.ui.homePage.dataMonitor.DataMonitorContract
import com.matej.roadsurfacetopography.ui.homePage.map.MapContract
import org.koin.dsl.module

val presentationModule = module {
    factory<RegistrationContract.Presenter> { RegistrationPresenter(get()) }
    factory<LoginContract.Presenter> { LoginPresenter(get()) }
    factory<DataMonitorContract.Presenter> { DataMonitorPresenter(get(), get()) }
    factory<DataListContract.Presenter> { DataListPresenter(get(), get(), get(), get()) }
    factory<DataListFsContract.Presenter> { DataListFsPresenter(get(), get(), get()) }
    factory<MapContract.Presenter> { MapPresenter(get(), get(), get()) }
}