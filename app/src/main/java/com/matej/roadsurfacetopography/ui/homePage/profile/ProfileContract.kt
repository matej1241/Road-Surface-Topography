package com.matej.roadsurfacetopography.ui.homePage.profile

import androidx.recyclerview.widget.ItemTouchHelper
import com.matej.roadsurfacetopography.model.SensorDataDb

interface ProfileContract {

    interface View{
        fun onGetDataSuccessful(data: List<SensorDataDb>)
        fun onGetDataFailed()
        fun onSignOutSuccessful()
    }

    interface Presenter{
        fun setView(view: View)
        fun getCurrentUser(): String
        fun getSensorData(): List<SensorDataDb>
        fun getSensorFsData()
        fun signOut()
    }
}