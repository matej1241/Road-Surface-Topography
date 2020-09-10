package com.matej.roadsurfacetopography.ui.homePage.dataMonitor

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager

import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.model.SensorData
import com.matej.roadsurfacetopography.ui.adapter.SensorDataAdapter
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_data_monitor.*
import org.koin.android.ext.android.inject
import java.text.DecimalFormat


class DataMonitorFragment : BaseFragment(), DataMonitorContract.View {

    private val adapter by lazy { SensorDataAdapter() }
    private val presenter: DataMonitorContract.Presenter by inject()

    private val TAG = "Sensors"

    private val decimalFormat = DecimalFormat("0.00")
    private lateinit var sensorManager: SensorManager;
    private lateinit var accelerationSensor: Sensor

    private val sensorListener = object: SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }
        override fun onSensorChanged(event: SensorEvent?) {
            val values = event?.values ?: floatArrayOf(0.0f, 0.0f, 0.0f)
            updateUi(values)
        }
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_data_monitor

    override fun setupUi() {
        presenter.setView(this)
        dataRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dataRecyclerView.adapter = adapter
        sensorManager = getActivity()?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(sensorListener, accelerationSensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorListener)
    }

    private fun updateUi(values: FloatArray) {

        val totalAcceleration = Math.sqrt(values.map { x -> x*x }.sum().toDouble())
        accelerationDisplay.text = decimalFormat.format(totalAcceleration)
        if(totalAcceleration > 12){
            adapter.addData(SensorData(totalAcceleration.toInt(), locationX = "", locationY = ""))
            Log.d(TAG, totalAcceleration.toString())
        }
        else if(totalAcceleration < 6){
            adapter.addData(SensorData(totalAcceleration.toInt(), locationX = "", locationY = ""))
            Log.d(TAG, totalAcceleration.toString())
        }

        accelerationComponents.text = ""
        values.forEach { accelerationComponents.append("${decimalFormat.format(it)}\n") }
    }

    override fun setOnClickListeners() {
    }

    companion object {
        fun newInstance(): Fragment {
            return DataMonitorFragment()
        }
    }
}
