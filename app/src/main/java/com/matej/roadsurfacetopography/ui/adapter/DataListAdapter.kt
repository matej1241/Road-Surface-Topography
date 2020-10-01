package com.matej.roadsurfacetopography.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.common.onLocationClickedListener
import com.matej.roadsurfacetopography.model.SensorData
import com.matej.roadsurfacetopography.model.SensorDataDb
import kotlinx.android.synthetic.main.item_data.view.*

class DataListAdapter(
    private val locationListener: onLocationClickedListener
): RecyclerView.Adapter<DataListHolder>()  {

    private val sensorData: MutableList<SensorDataDb> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sensor_data, parent, false)
        return DataListHolder(view)
    }

    override fun getItemCount(): Int = sensorData.size

    override fun onBindViewHolder(holder: DataListHolder, position: Int) {
        holder.bindData(sensorData[position], locationListener)
    }

    fun setData(data: List<SensorDataDb>) {
        this.sensorData.clear()
        this.sensorData.addAll(data)
        notifyDataSetChanged()
    }


}

class DataListHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bindData(data: SensorDataDb, locationListener: onLocationClickedListener){
        itemView.setOnClickListener { locationListener(data) }
        itemView.dataValueText.text = data.sensorValue.toString()
        itemView.locationXValueText.text = data.locationX.toString()
        itemView.locationYValueText.text = data.locationY.toString()
    }
}