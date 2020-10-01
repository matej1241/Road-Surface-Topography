package com.matej.roadsurfacetopography.presentation

import com.matej.roadsurfacetopography.domain.database.SensorDataDbInteractor
import com.matej.roadsurfacetopography.domain.firebase.CurrentUserUseCase
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.ui.homePage.map.MapContract

class MapPresenter(
    private val sensorDataDbInteractor: SensorDataDbInteractor,
    private val currentUserUseCase: CurrentUserUseCase
): MapContract.Presenter {

    private lateinit var view: MapContract.View

    override fun setView(view: MapContract.View) {
        this.view = view
    }

    override fun getCurrentUser(): String = currentUserUseCase.execute()

    override fun getSensorData(): List<SensorDataDb> = sensorDataDbInteractor.getAllSensorData(getCurrentUser())
}