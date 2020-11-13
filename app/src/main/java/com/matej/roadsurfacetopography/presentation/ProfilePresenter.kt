package com.matej.roadsurfacetopography.presentation

import com.google.firebase.firestore.QuerySnapshot
import com.matej.roadsurfacetopography.domain.authentication.LogoutUseCase
import com.matej.roadsurfacetopography.domain.database.SensorDataDbInteractor
import com.matej.roadsurfacetopography.domain.firebase.CurrentUserUseCase
import com.matej.roadsurfacetopography.domain.firestore.FirestoreUseCase
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.persistance.listenerRepository.ListenerRepository
import com.matej.roadsurfacetopography.ui.homePage.map.MapContract
import com.matej.roadsurfacetopography.ui.homePage.profile.ProfileContract

class ProfilePresenter(
    private val currentUserUseCase: CurrentUserUseCase,
    private val firestoreUseCase: FirestoreUseCase,
    private val sensorDataDbInteractor: SensorDataDbInteractor,
    private val logoutUseCase: LogoutUseCase
): ProfileContract.Presenter {

    private lateinit var view: ProfileContract.View

    override fun setView(view: ProfileContract.View) {
        this.view = view
    }

    override fun getCurrentUser(): String = currentUserUseCase.execute()

    override fun getSensorData(): List<SensorDataDb> = sensorDataDbInteractor.getAllSensorData(getCurrentUser())

    override fun getSensorFsData() = firestoreUseCase.getData(getCurrentUser(), ::onGetDataSuccessful, ::onGetDataFailed)

    override fun signOut() = logoutUseCase.execute(::onSignOutSuccessful)

    private fun onGetDataSuccessful(data: QuerySnapshot) {
        val dataList = mutableListOf<SensorDataDb>()
        for (document in data){
            dataList.add(
                SensorDataDb(
                    id = document.data.getValue("id").toString().toLong(),
                    locationX = document.data.getValue("latitude").toString().toDouble(),
                    locationY = document.data.getValue("longitude").toString().toDouble(),
                    sensorValue = document.data.getValue("sensorValue").toString().toDouble(),
                    user = document.data.getValue("user").toString(),
                    bumpType = document.data.getValue("bump_type").toString().toInt()
                ))
        }
        view.onGetDataSuccessful(dataList)
    }

    private fun onGetDataFailed() = view.onGetDataFailed()

    private fun onSignOutSuccessful() = view.onSignOutSuccessful()
}