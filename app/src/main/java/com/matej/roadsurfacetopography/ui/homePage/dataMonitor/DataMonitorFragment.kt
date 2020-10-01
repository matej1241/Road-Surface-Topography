package com.matej.roadsurfacetopography.ui.homePage.dataMonitor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.RoadSurfaceTopography
import com.matej.roadsurfacetopography.model.SensorData
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.ui.adapter.SensorDataAdapter
import com.matej.roadsurfacetopography.ui.base.BaseFragment
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
    private lateinit var sensorManager: SensorManager;
    private lateinit var accelerationSensor: Sensor
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback


    private val sensorListener = object: SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }
        override fun onSensorChanged(event: SensorEvent?) {
            val values = event?.values ?: floatArrayOf(0.0f, 0.0f, 0.0f)
            Log.d("+++", Thread.currentThread().name)
            updateUi(values)
        }
    }



    override fun getLayoutResourceId(): Int = R.layout.fragment_data_monitor

    override fun setupUi() {
        presenter.setView(this)
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

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    latitude = location.latitude
                    longitude = location.longitude
                    Log.d("+++", Thread.currentThread().name)
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
                    Log.d("+++", latitude.toString())
                    Log.d("+++", longitude.toString())
                }
            }
    }

    override fun onResume() {
        super.onResume()

        sensorManager.registerListener(sensorListener, accelerationSensor, 3)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorListener)
    }

    private fun updateUi(values: FloatArray) {

        val totalAcceleration = Math.sqrt(values.map { x -> x*x }.sum().toDouble())
        accelerationDisplay.text = decimalFormat.format(totalAcceleration)
        if(totalAcceleration > 13){
            if (lastLat == latitude) return
            adapter.addData(SensorData(totalAcceleration, locationX = latitude, locationY = longitude))
            presenter.saveSensorData(SensorDataDb(user = user, sensorValue = totalAcceleration, locationX = latitude, locationY = longitude))
            lastLat = latitude
            lastLong = longitude
            Log.d(TAG, totalAcceleration.toString())
        }
        else if(totalAcceleration < 6){
            if (lastLat == latitude) return
            adapter.addData(SensorData(totalAcceleration, locationX = latitude, locationY = longitude))
            presenter.saveSensorData(SensorDataDb(user = user, sensorValue = totalAcceleration, locationX = latitude, locationY = longitude))
            lastLat = latitude
            lastLong = longitude
            Log.d(TAG, totalAcceleration.toString())
        }

        accelerationComponents.text = ""
        //values.forEach { accelerationComponents.append("${decimalFormat.format(it)}\n") }
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
