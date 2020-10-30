package com.matej.roadsurfacetopography.ui.homePage.dataList

import androidx.recyclerview.widget.ItemTouchHelper
import com.google.firebase.firestore.QuerySnapshot
import com.matej.roadsurfacetopography.common.onSwipeToDelete
import com.matej.roadsurfacetopography.model.SensorDataDb

interface DataListFsContract {

    interface View{
        fun onGetDataSuccessful(data: List<SensorDataDb>)
        fun onGetDataFailed()
    }

    interface Presenter{
        fun setView(view: View)
        fun getCurrentUser(): String
        fun getSensorData()
        fun removeFirestoreSensorData(id: Long)
        //fun setOnSwipeToDeleteListener(onSwipeToDelete: onSwipeToDelete): ItemTouchHelper.SimpleCallback
    }
}