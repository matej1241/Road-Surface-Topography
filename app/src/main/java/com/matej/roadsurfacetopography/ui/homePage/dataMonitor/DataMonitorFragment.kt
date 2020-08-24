package com.matej.roadsurfacetopography.ui.homePage.dataMonitor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager

import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.model.SensorData
import com.matej.roadsurfacetopography.ui.adapter.SensorDataAdapter
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_data_monitor.*


class DataMonitorFragment : BaseFragment() {

    private val adapter by lazy { SensorDataAdapter() }

    private val listofdata: MutableList<SensorData> = mutableListOf(
        SensorData(12, "test", "test"),
        SensorData(11, "test", "test"),
        SensorData(10, "test", "test"),
        SensorData(9, "test", "test"),
        SensorData(8, "test", "test"),
        SensorData(7, "test", "test")
    )

    override fun getLayoutResourceId(): Int = R.layout.fragment_data_monitor

    override fun setupUi() {
        dataRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        dataRecyclerView.adapter = adapter
        adapter.setData(listofdata)
    }

    override fun setOnClickListeners() {
    }

    companion object {
        fun newInstance(): Fragment {
            return DataMonitorFragment()
        }
    }
}
