package com.matej.roadsurfacetopography.persistance.fragmentDataRepository

import com.matej.roadsurfacetopography.model.SensorDataDb

class FragmentDataRepository {
    companion object{
        private var selectedLatitude = 0.00
        private var selectedLongitude = 0.00
        private var data: SensorDataDb = SensorDataDb()
    }

    fun onLocationSelected(clickedData: SensorDataDb){
        data = clickedData
    }

    fun getSelectedLocation(): Pair<Double, Double> = Pair(selectedLatitude, selectedLongitude)

    fun getSelectedData(): SensorDataDb = data
}