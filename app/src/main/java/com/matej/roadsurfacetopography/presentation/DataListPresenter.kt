package com.matej.roadsurfacetopography.presentation

import com.matej.roadsurfacetopography.domain.database.SensorDataDbInteractor
import com.matej.roadsurfacetopography.domain.firebase.CurrentUserUseCase
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.ui.homePage.dataList.DataListContract

class DataListPresenter(
    private val sensorDataDbInteractor: SensorDataDbInteractor,
    private val currentUserUseCase: CurrentUserUseCase
): DataListContract.Presenter {

    private lateinit var view: DataListContract.View

    override fun setView(view: DataListContract.View) {
        this.view = view
    }

    override fun getCurrentUser(): String = currentUserUseCase.execute()

    override fun getSensorData(): List<SensorDataDb> = sensorDataDbInteractor.getAllSensorData(getCurrentUser())
}