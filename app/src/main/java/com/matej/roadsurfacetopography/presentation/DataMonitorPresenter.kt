package com.matej.roadsurfacetopography.presentation

import com.matej.roadsurfacetopography.domain.database.SensorDataDbInteractor
import com.matej.roadsurfacetopography.ui.homePage.dataMonitor.DataMonitorContract

class DataMonitorPresenter(
    private val sensorDataDbInteractor: SensorDataDbInteractor
): DataMonitorContract.Presenter {

    private lateinit var view: DataMonitorContract.View

    override fun setView(view: DataMonitorContract.View) {
        this.view = view
    }
}