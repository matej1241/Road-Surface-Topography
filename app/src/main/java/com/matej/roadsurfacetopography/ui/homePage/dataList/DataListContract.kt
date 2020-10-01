package com.matej.roadsurfacetopography.ui.homePage.dataList

import com.matej.roadsurfacetopography.model.SensorDataDb

interface DataListContract {

    interface View{
    }

    interface Presenter{
        fun setView(view: View)
        fun getCurrentUser(): String
        fun getSensorData(): List<SensorDataDb>
    }
}