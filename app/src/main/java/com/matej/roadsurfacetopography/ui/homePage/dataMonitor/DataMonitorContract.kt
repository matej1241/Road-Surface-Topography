package com.matej.roadsurfacetopography.ui.homePage.dataMonitor

import com.matej.roadsurfacetopography.model.SensorDataDb

interface DataMonitorContract {

    interface View{
    }

    interface Presenter{
        fun setView(view: View)
        fun getCurrentUser(): String
        fun saveSensorData(sensorData: SensorDataDb)
    }
}