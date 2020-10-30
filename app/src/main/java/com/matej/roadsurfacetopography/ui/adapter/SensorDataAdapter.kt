package com.matej.roadsurfacetopography.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.model.SensorData
import kotlinx.android.synthetic.main.item_data.view.*

class SensorDataAdapter: RecyclerView.Adapter<SensorDataHolder>() {

    private val sensorData: MutableList<SensorData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorDataHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return SensorDataHolder(view)
    }

    override fun getItemCount(): Int = sensorData.size

    override fun onBindViewHolder(holder: SensorDataHolder, position: Int) {
        holder.bindData(sensorData[position])
    }

    fun setData(data: List<SensorData>) {
        this.sensorData.clear()
        this.sensorData.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(sensorData: SensorData) {
        this.sensorData.add(sensorData)
        refreshData()
    }

    private fun refreshData(){
        notifyDataSetChanged()
    }
}

class SensorDataHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bindData(data: SensorData){
        itemView.dataValueText.text = data.value.toString()
        itemView.locationXValueText.text = data.locationX.toString()
        itemView.locationYValueText.text = data.locationY.toString()
        when(data.type){
            1->itemView.dataValueText.setTextColor(Color.rgb(12, 161, 2))
            2->itemView.dataValueText.setTextColor(Color.rgb(172, 224, 0))
            3->itemView.dataValueText.setTextColor(Color.rgb(224, 209, 0))
            4->itemView.dataValueText.setTextColor(Color.rgb(224, 138, 0))
            5->itemView.dataValueText.setTextColor(Color.rgb(224, 0, 0))
        }
    }
}