package com.matej.roadsurfacetopography.ui.homePage.map

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
import com.matej.roadsurfacetopography.persistance.fragmentDataRepository.FragmentDataRepository
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import com.matej.roadsurfacetopography.ui.homePage.HomePageActivity
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.android.ext.android.inject


class MapFragment : BaseFragment(), OnMapReadyCallback, MapContract.View {

    private val presenter: MapContract.Presenter by inject()
    private val repository = FragmentDataRepository()

    override fun getLayoutResourceId(): Int = R.layout.fragment_map

    override fun setupUi() {
        presenter.setView(this)
        (activity as HomePageActivity?)?.supportActionBar?.hide()
        initGoogleMap()
    }

    private fun initGoogleMap() {
        val mapViewBundle: Bundle? = null
        mapView.onCreate(mapViewBundle)
        mapView?.getMapAsync(this)
    }

    override fun setOnClickListeners() {
        localDataChip.setOnCloseIconClickListener { onGetLocalDataClicked() }
    }

    private fun onGetLocalDataClicked() {
        localDataChip.setChipBackgroundColorResource(R.color.colorPrimary)
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
        when{
            isDataClicked -> onLocationClicked(map)
            else -> onShowAllLocationsClicked(map)
        }
    }

    private fun setCamera(map: GoogleMap, selectedLocation: Pair<Double, Double>) {
        val location = LatLng(selectedLocation.first, selectedLocation.second)
        val cameraPosition = CameraPosition.Builder().target(location).zoom(17.0F).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private fun onLocationClicked(map: GoogleMap){
        val location = repository.getSelectedLocation()
        setCamera(map, location)
        map.addMarker(MarkerOptions().position(LatLng(location.first, location.second)).title("marker"))
        isDataClicked = false
    }

    private fun onShowAllLocationsClicked(map: GoogleMap) {
        val locations = presenter.getSensorData()
        setCamera(map, Pair(locations[0].locationX, locations[0].locationY))
        for (location in locations){
            when(location.bumpType){
                SMALL_BUMP -> addMarker(map, R.drawable.ic_small_bump_marker, location.locationX, location.locationY)
                MEDIUM_SMALL_BUMP -> addMarker(map, R.drawable.ic_medium_small_bump_marker, location.locationX, location.locationY)
                MEDIUM_BUMP -> addMarker(map, R.drawable.ic_medium_bump_marker, location.locationX, location.locationY)
                MEDIUM_BIG_BUMP -> addMarker(map, R.drawable.ic_medium_big_bump_marker, location.locationX, location.locationY)
                BIG_BUMP -> addMarker(map, R.drawable.ic_big_bump_marker, location.locationX, location.locationY)
            }
        }
    }

    private fun addMarker(map: GoogleMap, markerIcon: Int, latitude: Double, longitude: Double){
        map.addMarker(MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(markerIcon))
            .position(LatLng(latitude, longitude)).title("marker"))
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

    companion object {
        var isDataClicked = false

        fun newInstance(): Fragment {
            return MapFragment()
        }
    }
}
