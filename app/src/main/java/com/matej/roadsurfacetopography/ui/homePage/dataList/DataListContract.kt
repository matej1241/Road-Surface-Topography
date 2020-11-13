package com.matej.roadsurfacetopography.ui.homePage.dataList

import androidx.recyclerview.widget.ItemTouchHelper
import com.matej.roadsurfacetopography.common.onSwipeToDelete
import com.matej.roadsurfacetopography.common.onSwipeToSave
import com.matej.roadsurfacetopography.model.SensorDataDb

interface DataListContract {

    interface View{
        fun onSaveDataSuccessful()
        fun onSaveDataFailed()
    }

    interface Presenter{
        fun setView(view: View)
        fun getCurrentUser(): String
        fun getSensorData(): List<SensorDataDb>
        fun removeSensorData(id: Long)
        fun saveSensorData(data: SensorDataDb)
        fun setOnSwipeToDeleteListener(onSwipeToDelete: onSwipeToDelete): ItemTouchHelper.SimpleCallback
        fun setOnSwipeToSaveListener(onSwipeToSave: onSwipeToSave): ItemTouchHelper.SimpleCallback
        fun removeAllSensorData()
    }
}