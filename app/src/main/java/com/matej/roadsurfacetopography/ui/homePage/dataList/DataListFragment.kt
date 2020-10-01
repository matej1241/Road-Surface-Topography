package com.matej.roadsurfacetopography.ui.homePage.dataList

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.common.showFragment
import com.matej.roadsurfacetopography.model.SensorData
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.persistance.fragmentDataRepository.FragmentDataRepository
import com.matej.roadsurfacetopography.ui.adapter.DataListAdapter
import com.matej.roadsurfacetopography.ui.base.BaseFragment
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
    }

    private fun onLocationClicked(data: SensorDataDb){
        repository.onLocationSelected(data.locationX, data.locationY)
        activity?.showFragment(R.id.homeFragmentContainer, MapFragment.newInstance())
    }

    companion object {
        fun newInstance(): Fragment {
            return DataListFragment()
        }
    }
}
