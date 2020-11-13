package com.matej.roadsurfacetopography.ui.homePage.dataList

import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.ui.adapter.DataListPagerAdapter
import com.matej.roadsurfacetopography.ui.adapter.SensorDataAdapter
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import com.matej.roadsurfacetopography.ui.homePage.HomePageActivity
import kotlinx.android.synthetic.main.fragment_data_list_container.*

class DataListContainerFragment : BaseFragment() {

    override fun getLayoutResourceId(): Int = R.layout.fragment_data_list_container

    override fun setupUi() {
        viewPager.adapter = DataListPagerAdapter(activity!!.supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        (activity as HomePageActivity?)?.supportActionBar?.show()
    }

    override fun setOnClickListeners() {
    }

    companion object{
        fun newInstance(): Fragment = DataListContainerFragment()
    }
}
