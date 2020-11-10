package com.matej.roadsurfacetopography.presentation

import com.google.firebase.firestore.QuerySnapshot
import com.matej.roadsurfacetopography.domain.database.SensorDataDbInteractor
import com.matej.roadsurfacetopography.domain.firebase.CurrentUserUseCase
import com.matej.roadsurfacetopography.domain.firestore.FirestoreUseCase
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.ui.homePage.map.MapContract

class MapPresenter(
    private val sensorDataDbInteractor: SensorDataDbInteractor,
    private val currentUserUseCase: CurrentUserUseCase,
    private val firestoreUseCase: FirestoreUseCase
): MapContract.Presenter {

    private lateinit var view: MapContract.View

    override fun setView(view: MapContract.View) {
        this.view = view
    }

    override fun getCurrentUser(): String = currentUserUseCase.execute()

    override fun getSensorData(): List<SensorDataDb> = sensorDataDbInteractor.getAllSensorData(getCurrentUser())

    override fun getFsSensorData(){
        firestoreUseCase.getData(getCurrentUser(), ::onGetDataSuccessful, ::onGetDataFailed)
    }

    override fun getAllFsSensorData() {
        firestoreUseCase.getAllData(::onGetAllDataSuccessful, ::onGetAllDataFailed)
    }

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

    private fun onGetAllDataSuccessful(data: QuerySnapshot) {
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
        view.onGetAllDataSuccessful(dataList)
    }

    private fun onGetAllDataFailed() = view.onGetAllDataFailed()
}