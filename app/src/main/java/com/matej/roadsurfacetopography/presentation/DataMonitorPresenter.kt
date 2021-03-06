package com.matej.roadsurfacetopography.presentation

import com.matej.roadsurfacetopography.domain.database.SensorDataDbInteractor
import com.matej.roadsurfacetopography.domain.firebase.CurrentUserUseCase
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.ui.homePage.dataMonitor.DataMonitorContract

class DataMonitorPresenter(
    private val sensorDataDbInteractor: SensorDataDbInteractor,
    private val currentUserUseCase: CurrentUserUseCase
): DataMonitorContract.Presenter {

    private lateinit var view: DataMonitorContract.View

    override fun setView(view: DataMonitorContract.View) {
        this.view = view
    }

    override fun getCurrentUser(): String = currentUserUseCase.execute()

    override fun saveSensorData(sensorData: SensorDataDb) {
        sensorDataDbInteractor.insertSensorData(sensorData)
    }

}