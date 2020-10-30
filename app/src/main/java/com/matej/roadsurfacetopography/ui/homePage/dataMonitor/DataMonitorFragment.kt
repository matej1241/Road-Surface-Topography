package com.matej.roadsurfacetopography.ui.homePage.dataMonitor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.HandlerThread
import android.util.Log
import android.widget.Toast
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
    private val TAG = "Sensors"
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val decimalFormat = DecimalFormat("0.00")
    private var latitude = 0.00
    private var longitude = 0.00
    private var lastLat = 0.00
    private var lastLong = 0.00
    private var currentLat = 0.00
    private var currentLong = 0.00
    private lateinit var sensorManager: SensorManager;
    private lateinit var accelerationSensor: Sensor
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var recordedData = mutableListOf<Double>()

    override fun getLayoutResourceId(): Int = R.layout.fragment_data_monitor

    override fun setupUi() {
        presenter.setView(this)
        (activity as HomePageActivity?)?.supportActionBar?.show()
        createLocationRequest()
        createLocationCallback()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(RoadSurfaceTopography.instance)
        dataRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dataRecyclerView.adapter = adapter
        user = presenter.getCurrentUser()
        startLocationUpdates()
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
                if (value > 13 || value < 7){
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
                    updateUi(values, recordedData)
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
                }
            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 500
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun startLocationUpdates() {
        val sensorThread = HandlerThread("Sensor thread")
        sensorThread.start()
        val looper = sensorThread.looper
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, looper)
    }

    fun getLastKnownLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location->
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                }
            }
    }

    override fun onResume() {
        super.onResume()

        sensorManager.registerListener(sensorListener, accelerationSensor, 0)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorListener)
    }

    private fun updateUi(values: FloatArray, recordedData: List<Double>) {

        Toast.makeText(RoadSurfaceTopography.instance, recordedData.max().toString() + recordedData.min().toString(), Toast.LENGTH_SHORT).show()
        val max = recordedData.max()!!.toDouble()
        val min = recordedData.min()!!.toDouble()
        val difference = max - min
        Log.d("+++", max.toString())
        Log.d("+++", min.toString())
        Log.d("+++", difference.toString())
        when{
            difference > 5 && difference < 8 -> saveData(difference, SMALL_BUMP)
            difference > 8 && difference < 11 -> saveData(difference, MEDIUM_SMALL_BUMP)
            difference > 11 && difference < 14 -> saveData(difference, MEDIUM_BUMP)
            difference > 14 && difference < 17 -> saveData(difference, MEDIUM_BIG_BUMP)
            difference > 17 -> saveData(difference, BIG_BUMP)
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
    }

    companion object {
        fun newInstance(): Fragment {
            return DataMonitorFragment()
        }
        private lateinit var user: String
    }
}
