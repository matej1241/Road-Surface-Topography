package com.matej.roadsurfacetopography.ui.homePage.map

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.common.MAPVIEW_BUNDLE_KEY
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.persistance.dbRepository.SensorDataRepository
import com.matej.roadsurfacetopography.persistance.dbRepository.SensorDataRepositoryImpl
import com.matej.roadsurfacetopography.persistance.fragmentDataRepository.FragmentDataRepository
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import com.matej.roadsurfacetopography.ui.homePage.dataMonitor.DataMonitorContract
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.android.ext.android.inject


class MapFragment : BaseFragment(), OnMapReadyCallback, MapContract.View {

    private val presenter: MapContract.Presenter by inject()
    private val repository = FragmentDataRepository()

    override fun getLayoutResourceId(): Int = R.layout.fragment_map

    override fun setupUi() {
        presenter.setView(this)
        initGoogleMap()
    }

    private fun initGoogleMap() {
        val mapViewBundle: Bundle? = null
        mapView.onCreate(mapViewBundle)
        mapView?.getMapAsync(this)
    }

    override fun setOnClickListeners() {
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

    override fun onMapReady(map: GoogleMap) {
        //val location = repository.getSelectedLocation()
        //map.addMarker(MarkerOptions().position(LatLng(location.first, location.second)).title("marker"))
        val locations = presenter.getSensorData()
        setCamera(map, locations)
        for (location in locations){
            map.addMarker(MarkerOptions().position(LatLng(location.locationX, location.locationY)).title("marker"))
        }
    }

    private fun setCamera(map: GoogleMap, locations: List<SensorDataDb>) {
        val location = LatLng(locations.last().locationX, locations.last().locationY)
        val cameraPosition = CameraPosition.Builder().target(location).zoom(17.0F).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
        fun newInstance(): Fragment {
            return MapFragment()
        }
    }
}
