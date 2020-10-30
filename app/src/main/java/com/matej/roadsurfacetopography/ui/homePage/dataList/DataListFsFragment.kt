package com.matej.roadsurfacetopography.ui.homePage.dataList


import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.RoadSurfaceTopography
import com.matej.roadsurfacetopography.common.showFragment
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.persistance.fragmentDataRepository.FragmentDataRepository
import com.matej.roadsurfacetopography.ui.adapter.DataListAdapter
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import com.matej.roadsurfacetopography.ui.homePage.map.MapFragment
import kotlinx.android.synthetic.main.fragment_data_list.*
import kotlinx.android.synthetic.main.fragment_data_list_fs.*
import org.koin.android.ext.android.inject

class DataListFsFragment : BaseFragment(), DataListFsContract.View {

    private val presenter: DataListFsContract.Presenter by inject()
    private val adapter by lazy { DataListAdapter(::onLocationClicked) }
    private val repository = FragmentDataRepository()

    override fun getLayoutResourceId(): Int = R.layout.fragment_data_list_fs

    override fun setupUi() {
        presenter.setView(this)
        setRecyclerView()
        presenter.getSensorData()
    }

    override fun setOnClickListeners() {
    }

    override fun onGetDataSuccessful(data: List<SensorDataDb>) {
        adapter.setData(data)
    }

    override fun onGetDataFailed() {
    }

    private fun onLocationClicked(data: SensorDataDb){
        repository.onLocationSelected(data.locationX, data.locationY)
        MapFragment.isDataClicked = true
        activity?.showFragment(R.id.homeFragmentContainer, MapFragment.newInstance())
    }

    private fun setRecyclerView() {
        dataListFsRecyclerView.layoutManager = LinearLayoutManager(context)
        dataListFsRecyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): Fragment = DataListFsFragment()
    }
}
