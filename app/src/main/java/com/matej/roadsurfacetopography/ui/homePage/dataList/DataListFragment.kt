package com.matej.roadsurfacetopography.ui.homePage.dataList

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.RoadSurfaceTopography
import com.matej.roadsurfacetopography.common.showFragment
import com.matej.roadsurfacetopography.model.SensorData
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.persistance.fragmentDataRepository.FragmentDataRepository
import com.matej.roadsurfacetopography.ui.adapter.DataListAdapter
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import com.matej.roadsurfacetopography.ui.homePage.HomePageActivity
import com.matej.roadsurfacetopography.ui.homePage.map.MapFragment
import kotlinx.android.synthetic.main.fragment_data_list.*
import org.koin.android.ext.android.inject


class DataListFragment : BaseFragment(), DataListContract.View {

    private val adapter by lazy { DataListAdapter(::onLocationClicked) }
    private val presenter: DataListContract.Presenter by inject()
    private val repository = FragmentDataRepository()

    override fun getLayoutResourceId(): Int = R.layout.fragment_data_list

    override fun setupUi() {
        presenter.setView(this)
        setRecyclerView()
        adapter.setData(presenter.getSensorData())
    }

    private fun setRecyclerView() {
        dataListRecyclerView.layoutManager = LinearLayoutManager(context)
        dataListRecyclerView.adapter = adapter
    }

    override fun setOnClickListeners() {
        swipeToDeleteListener()
        swipeToSaveListener()
    }

    private fun onLocationClicked(data: SensorDataDb){
        repository.onLocationSelected(data.locationX, data.locationY)
        MapFragment.isDataClicked = true
        activity?.showFragment(R.id.homeFragmentContainer, MapFragment.newInstance())
    }

    private fun swipeToDeleteListener() {
        val itemTouchHelper = ItemTouchHelper(presenter.setOnSwipeToDeleteListener(::onSwipeToDelete))
        itemTouchHelper.attachToRecyclerView(dataListRecyclerView)
    }

    private fun swipeToSaveListener() {
        val itemTouchHelper = ItemTouchHelper(presenter.setOnSwipeToSaveListener(::onSwipeToSave))
        itemTouchHelper.attachToRecyclerView(dataListRecyclerView)
    }

    private fun onSwipeToSave(position: Int){
        val currentData = adapter.getCurrentItem(position)
        adapter.removeItem(currentData)
        presenter.saveSensorData(currentData)
        presenter.removeSensorData(currentData.id)
    }

    private fun onSwipeToDelete(position: Int) {
        val currentData = adapter.getCurrentItem(position)
        adapter.removeItem(currentData)
        presenter.removeSensorData(currentData.id)
    }

    override fun onSaveDataSuccessful() {
        Toast.makeText(RoadSurfaceTopography.instance, "Data saved successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onSaveDataFailed() {
        Toast.makeText(RoadSurfaceTopography.instance, "Data save has failed", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): Fragment {
            return DataListFragment()
        }
    }
}
