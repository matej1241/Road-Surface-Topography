package com.matej.roadsurfacetopography.ui.homePage.dataMonitor

import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.Fragment

import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import com.matej.roadsurfacetopography.ui.homePage.dataList.DataListFsContract
import kotlinx.android.synthetic.main.fragment_data_monitor_settings.*


class DataMonitorSettingsFragment : BaseFragment() {

    override fun getLayoutResourceId(): Int = R.layout.fragment_data_monitor_settings

    override fun setupUi() {
        when(DataMonitorFragment.locationUpdateFrequency){
            250 -> radioButton1.isChecked = true
            500 -> radioButton2.isChecked = true
            750 -> radioButton3.isChecked = true
            1000 -> radioButton4.isChecked = true
        }
    }

    override fun setOnClickListeners() {
        radioButton1.setOnClickListener { onRadioButtonClicked(250) }
        radioButton2.setOnClickListener { onRadioButtonClicked(500) }
        radioButton3.setOnClickListener { onRadioButtonClicked(750) }
        radioButton4.setOnClickListener { onRadioButtonClicked(1000) }
    }

    private fun onRadioButtonClicked(frequency: Int){
        DataMonitorFragment.locationUpdateFrequency = frequency
    }

    companion object{
        fun newInstance(): Fragment = DataMonitorSettingsFragment()
    }
}
