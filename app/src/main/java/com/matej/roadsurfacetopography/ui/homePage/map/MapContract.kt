package com.matej.roadsurfacetopography.ui.homePage.map

import com.matej.roadsurfacetopography.model.SensorData
import com.matej.roadsurfacetopography.model.SensorDataDb

interface MapContract {

    interface View{
    }

    interface Presenter{
        fun setView(view: View)
        fun getCurrentUser(): String
        fun getSensorData(): List<SensorDataDb>
    }
}