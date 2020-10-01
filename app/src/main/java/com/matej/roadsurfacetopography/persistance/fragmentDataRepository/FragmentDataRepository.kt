package com.matej.roadsurfacetopography.persistance.fragmentDataRepository

class FragmentDataRepository {
    companion object{
        private var selectedLatitude = 0.00
        private var selectedLongitude = 0.00
    }

    fun onLocationSelected(latitude: Double, longitude: Double){
        selectedLatitude = latitude
        selectedLongitude = longitude
    }

    fun getSelectedLocation(): Pair<Double, Double> = Pair(selectedLatitude, selectedLongitude)
}