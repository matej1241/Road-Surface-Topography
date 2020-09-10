package com.matej.roadsurfacetopography.ui.homePage.dataMonitor

interface DataMonitorContract {

    interface View{
    }

    interface Presenter{
        fun setView(view: View)
    }
}