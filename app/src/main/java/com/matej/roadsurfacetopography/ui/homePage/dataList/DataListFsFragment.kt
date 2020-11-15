package com.matej.roadsurfacetopography.ui.homePage.dataList


import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.common.showFragment
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.persistance.fragmentDataRepository.FragmentDataRepository
import com.matej.roadsurfacetopography.ui.adapter.DataListAdapter
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import com.matej.roadsurfacetopography.ui.homePage.map.MapFragment
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
        getDataProgress.visibility = View.VISIBLE
    }

    override fun setOnClickListeners() {
        swipeToDeleteListener()
        pullToRefresh.setOnRefreshListener { onPullToRefresh() }
    }

    override fun onGetDataSuccessful(data: List<SensorDataDb>) {
        getDataProgress.visibility = View.GONE
        adapter.setData(data)
    }

    override fun onGetDataFailed() {
        getDataProgress.visibility = View.GONE
        Log.d("+++", "Failed to get data")
    }

    private fun onLocationClicked(data: SensorDataDb){
        repository.onLocationSelected(data)
        MapFragment.isDataClicked = true
        activity?.showFragment(R.id.homeFragmentContainer, MapFragment.newInstance(), true)
    }

    private fun swipeToDeleteListener() {
        val itemTouchHelper = ItemTouchHelper(presenter.setOnSwipeToDeleteListener(::onSwipeToDelete))
        itemTouchHelper.attachToRecyclerView(dataListFsRecyclerView)
    }

    private fun onSwipeToDelete(position: Int) {
        val currentData = adapter.getCurrentItem(position)
        adapter.removeItem(currentData)
        presenter.removeFirestoreSensorData(currentData.fsId)
    }

    private fun onPullToRefresh() {
        presenter.getSensorData()
        pullToRefresh.isRefreshing = false
    }

    private fun setRecyclerView() {
        dataListFsRecyclerView.layoutManager = LinearLayoutManager(context)
        dataListFsRecyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): Fragment = DataListFsFragment()
     }
}
