package com.matej.roadsurfacetopography.ui.homePage.dataMonitor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_FASTEST
import android.os.HandlerThread
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.RoadSurfaceTopography
import com.matej.roadsurfacetopography.common.*
import com.matej.roadsurfacetopography.model.SensorData
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.ui.adapter.SensorDataAdapter
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import com.matej.roadsurfacetopography.ui.homePage.HomePageActivity
import kotlinx.android.synthetic.main.fragment_data_monitor.*
import org.koin.android.ext.android.inject
import java.text.DecimalFormat


class DataMonitorFragment : BaseFragment(), DataMonitorContract.View {

    private val adapter by lazy { SensorDataAdapter() }
    private val presenter: DataMonitorContract.Presenter by inject()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val decimalFormat = DecimalFormat("0.00")
    private var latitude = 0.00
    private var longitude = 0.00
    private var currentLat = 0.00
    private var currentLong = 0.00
    private lateinit var sensorManager: SensorManager;
    private lateinit var accelerationSensor: Sensor
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var buttonPressed = false
    private var recordedData = mutableListOf<Double>()

    override fun getLayoutResourceId(): Int = R.layout.fragment_data_monitor

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.data_monitor_menu, menu)
    }

    override fun setupUi() {
        presenter.setView(this)
        setHasOptionsMenu(true)
        (activity as HomePageActivity?)?.supportActionBar?.show()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(RoadSurfaceTopography.instance)
        dataRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dataRecyclerView.adapter = adapter
        user = presenter.getCurrentUser()
        sensorManager = getActivity()?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    private val sensorListener = object: SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }
        override fun onSensorChanged(event: SensorEvent?) {
            val values = event?.values ?: floatArrayOf(0.0f, 0.0f, 0.0f)
            val value = Math.sqrt(values.map { x -> x*x }.sum().toDouble())
            updateAccelerationText(value)
            if(recordedData.isEmpty()){
                if (value > LIMIT_TOP || value < LIMIT_BOTTOM){
                    currentLat = latitude
                    currentLong = longitude
                    recordedData.add(value)
                }
            }
            else{
                if(currentLat == latitude && currentLong == longitude){
                    recordedData.add(value)
                }
                else{
                    updateUi(recordedData)
                    recordedData.clear()
                }
            }
        }
    }

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    latitude = location.latitude
                    longitude = location.longitude
                    Log.d("+++", location.toString())
                }
            }
        }
    }

    private fun createLocationRequest(freqInterval: Int) {
        locationRequest = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = freqInterval.toLong()
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun startLocationUpdates() {
        val sensorThread = HandlerThread("location_thread")
        sensorThread.start()
        val looper = sensorThread.looper
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, looper)
    }

    private fun updateUi(recordedData: List<Double>) {
        val max = recordedData.max()!!.toDouble()
        val min = recordedData.min()!!.toDouble()
        val difference = max - min
        when{
            difference >= SMALL_BUMP_LOWER && difference < SMALL_BUMP_HIGHER -> saveData(difference, SMALL_BUMP)
            difference >= MED_SMALL_BUMP_LOWER && difference < MED_SMALL_BUMP_HIGHER -> saveData(difference, MEDIUM_SMALL_BUMP)
            difference >= MED_BUMP_LOWER && difference < MED_BUMP_HIGHER -> saveData(difference, MEDIUM_BUMP)
            difference >= MED_BIG_BUMP_LOWER && difference < MED_BIG_BUMP_HIGHER -> saveData(difference, MEDIUM_BIG_BUMP)
            difference >= BIG_BUMP_LOWER -> saveData(difference, BIG_BUMP)
        }
    }

    private fun saveData(difference: Double, bumpType: Int){
        adapter.addData(SensorData(difference, locationX = latitude, locationY = longitude, type = bumpType))
        presenter.saveSensorData(SensorDataDb(user = user, sensorValue = difference, locationX = latitude, locationY = longitude, bumpType = bumpType))
    }

    private fun updateAccelerationText(value: Double){
        accelerationDisplay.text = decimalFormat.format(value)
    }

    override fun setOnClickListeners() {
        startButton.setOnClickListener { onButtonClicked() }
    }

    private fun onButtonClicked() {
        if(!buttonPressed){
            sensorManager.registerListener(sensorListener, accelerationSensor, SENSOR_DELAY_FASTEST)
            createLocationRequest(locationUpdateFrequency)
            startLocationUpdates()
            startButton.text = "STOP"
            buttonPressed = true
        }else{
            sensorManager.unregisterListener(sensorListener)
            fusedLocationClient.removeLocationUpdates(locationCallback)
            startButton.text = "START"
            buttonPressed = false
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings -> activity?.showFragment(R.id.homeFragmentContainer, DataMonitorSettingsFragment.newInstance(), true)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorListener)
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        createLocationCallback()
    }

    companion object {
        fun newInstance(): Fragment {
            return DataMonitorFragment()
        }
        private lateinit var user: String
        var locationUpdateFrequency = 250
        const val LIMIT_TOP = 12.3
        const val LIMIT_BOTTOM = 7.3
    }
}
