package com.matej.roadsurfacetopography.presentation

import com.matej.roadsurfacetopography.common.onSwipeToDelete
import com.matej.roadsurfacetopography.common.onSwipeToSave
import com.matej.roadsurfacetopography.domain.database.SensorDataDbInteractor
import com.matej.roadsurfacetopography.domain.firebase.CurrentUserUseCase
import com.matej.roadsurfacetopography.domain.firestore.FirestoreUseCase
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.persistance.firebaseRepository.FirebaseRepository
import com.matej.roadsurfacetopography.persistance.listenerRepository.ListenerRepository
import com.matej.roadsurfacetopography.ui.homePage.dataList.DataListContract

class DataListPresenter(
    private val sensorDataDbInteractor: SensorDataDbInteractor,
    private val currentUserUseCase: CurrentUserUseCase,
    private val listenerRepository: ListenerRepository,
    private val firestoreUseCase: FirestoreUseCase
): DataListContract.Presenter {

    private lateinit var view: DataListContract.View

    override fun setView(view: DataListContract.View) {
        this.view = view
    }

    override fun getCurrentUser(): String = currentUserUseCase.execute()

    override fun getSensorData(): List<SensorDataDb> = sensorDataDbInteractor.getAllSensorData(getCurrentUser())

    override fun removeSensorData(id: Long) = sensorDataDbInteractor.removeSensorData(id)

    override fun removeAllSensorData() = sensorDataDbInteractor.removeAllSensorData()

    override fun saveSensorData(data: SensorDataDb) {
        firestoreUseCase.saveData(hashMapOf(
                "id" to data.id,
                "user" to getCurrentUser(),
                "sensorValue" to data.sensorValue,
                "latitude" to data.locationX,
                "longitude" to data.locationY,
                "bump_type" to data.bumpType
            ), ::onSaveDataSuccessful, ::onSaveDataFailed
        )
    }

    override fun setOnSwipeToDeleteListener(onSwipeToDelete: onSwipeToDelete) = listenerRepository.setOnSwipeToDeleteListener(onSwipeToDelete)

    override fun setOnSwipeToSaveListener(onSwipeToSave: onSwipeToSave) = listenerRepository.setOnSwipeToSaveListener(onSwipeToSave)

    private fun onSaveDataSuccessful() = view.onSaveDataSuccessful()

    private fun onSaveDataFailed() = view.onSaveDataFailed()
}