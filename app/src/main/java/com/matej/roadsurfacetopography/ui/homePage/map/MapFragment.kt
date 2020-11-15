package com.matej.roadsurfacetopography.ui.homePage.map

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.common.*
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.persistance.fragmentDataRepository.FragmentDataRepository
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import com.matej.roadsurfacetopography.ui.homePage.HomePageActivity
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.android.ext.android.inject


class MapFragment : BaseFragment(), OnMapReadyCallback, MapContract.View {

    private val presenter: MapContract.Presenter by inject()
    private val repository = FragmentDataRepository()
    private var localData: List<SensorDataDb> = mutableListOf()
    private var remoteData: List<SensorDataDb> = mutableListOf()
    private var allRemoteData: List<SensorDataDb> = mutableListOf()
    private lateinit var map: GoogleMap

    override fun getLayoutResourceId(): Int = R.layout.fragment_map

    override fun setupUi() {
        presenter.setView(this)
        (activity as HomePageActivity?)?.supportActionBar?.hide()
        localData = presenter.getSensorData()
        presenter.getFsSensorData()
        presenter.getAllFsSensorData()
        initGoogleMap()
    }

    private fun initGoogleMap() {
        val mapViewBundle: Bundle? = null
        mapView.onCreate(mapViewBundle)
        mapView?.getMapAsync(this)
    }

    override fun setOnClickListeners() {
        localDataChip.setOnClickListener { onGetLocalDataClicked() }
        remoteDataChip.setOnClickListener { onGetRemoteDataClicked() }
        allDataChip.setOnClickListener { onGetAllRemoteDataClicked() }
    }

    private fun onGetLocalDataClicked() {
        map.clear()
        localDataChip.isChecked = true
        showLocations(map, localData)
    }

    private fun onGetRemoteDataClicked() {
        map.clear()
        remoteDataChip.isChecked = true
        showLocations(map, remoteData)
    }

    private fun onGetAllRemoteDataClicked() {
        map.clear()
        allDataChip.isChecked = true
        showLocations(map, allRemoteData)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }
        mapView?.onSaveInstanceState(mapViewBundle)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        when{
            isDataClicked -> onLocationClicked(map)
            else -> {
                showLocations(map, localData)
                localDataChip.isChecked = true
            }
        }
    }

    private fun setCamera(map: GoogleMap, selectedLocation: Pair<Double, Double>) {
        val location = LatLng(selectedLocation.first, selectedLocation.second)
        val cameraPosition = CameraPosition.Builder().target(location).zoom(17.0F).build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun onLocationClicked(map: GoogleMap){
        val location = mutableListOf<SensorDataDb>(repository.getSelectedData())
        showLocations(map, location)
        isDataClicked = false
    }

    private fun showLocations(map: GoogleMap, locations: List<SensorDataDb>) {
        if(locations.isEmpty()) return
        setCamera(map, Pair(locations.last().locationX, locations.last().locationY))
        for (location in locations){
            when(location.bumpType){
                SMALL_BUMP -> addMarker(map, R.drawable.ic_small_bump_marker, location.locationX, location.locationY, location.sensorValue)
                MEDIUM_SMALL_BUMP -> addMarker(map, R.drawable.ic_medium_small_bump_marker, location.locationX, location.locationY, location.sensorValue)
                MEDIUM_BUMP -> addMarker(map, R.drawable.ic_medium_bump_marker, location.locationX, location.locationY, location.sensorValue)
                MEDIUM_BIG_BUMP -> addMarker(map, R.drawable.ic_medium_big_bump_marker, location.locationX, location.locationY, location.sensorValue)
                BIG_BUMP -> addMarker(map, R.drawable.ic_big_bump_marker, location.locationX, location.locationY, location.sensorValue)
            }
        }
    }

    private fun addMarker(map: GoogleMap, markerIcon: Int, latitude: Double, longitude: Double, sensorValue: Double){
        map.addMarker(MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(markerIcon))
            .position(LatLng(latitude, longitude)).title("Sensor value ${sensorValue.toString()}"))
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        mapView?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()

    }



    override fun onGetDataSuccessful(data: List<SensorDataDb>) {
        remoteData = data
    }

    override fun onGetDataFailed() {
        Log.d("+++", "Failed to get remote data")
    }

    override fun onGetAllDataSuccessful(data: List<SensorDataDb>) {
        allRemoteData = data
    }

    override fun onGetAllDataFailed() {
        Log.d("+++", "Failed to get remote data")
    }



    companion object {

        var isDataClicked = false
        fun newInstance(): Fragment {
            return MapFragment()
        }

    }
}
