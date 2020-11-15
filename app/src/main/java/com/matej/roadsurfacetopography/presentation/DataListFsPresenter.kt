package com.matej.roadsurfacetopography.presentation


import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.firebase.firestore.QuerySnapshot
import com.matej.roadsurfacetopography.common.onSwipeToDelete
import com.matej.roadsurfacetopography.domain.firebase.CurrentUserUseCase
import com.matej.roadsurfacetopography.domain.firestore.FirestoreUseCase
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.persistance.listenerRepository.ListenerRepository
import com.matej.roadsurfacetopography.ui.homePage.dataList.DataListFsContract

class DataListFsPresenter(
    private val currentUserUseCase: CurrentUserUseCase,
    private val firestoreUseCase: FirestoreUseCase,
    private val listenerRepository: ListenerRepository
): DataListFsContract.Presenter{

    private lateinit var view: DataListFsContract.View

    override fun setView(view: DataListFsContract.View) {
        this.view = view
    }

    override fun getCurrentUser(): String = currentUserUseCase.execute()

    override fun getSensorData(){
        firestoreUseCase.getData(getCurrentUser(), ::onGetDataSuccessful, ::onGetDataFailed)
    }

    override fun removeFirestoreSensorData(id: String) = firestoreUseCase.deleteData(id)

    override fun setOnSwipeToDeleteListener(onSwipeToDelete: onSwipeToDelete): ItemTouchHelper.SimpleCallback
            = listenerRepository.setOnSwipeToDeleteListener(onSwipeToDelete)

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
                    bumpType = document.data.getValue("bump_type").toString().toInt(),
                    fsId = document.id
            ))
        }
        view.onGetDataSuccessful(dataList)
    }

    private fun onGetDataFailed() = view.onGetDataFailed()
}