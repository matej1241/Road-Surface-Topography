package com.matej.roadsurfacetopography.ui.homePage

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.common.showFragment
import com.matej.roadsurfacetopography.ui.base.BaseActivity
import com.matej.roadsurfacetopography.ui.homePage.dataList.DataListFragment
import com.matej.roadsurfacetopography.ui.homePage.dataMonitor.DataMonitorFragment
import com.matej.roadsurfacetopography.ui.homePage.map.MapFragment
import com.matej.roadsurfacetopography.ui.homePage.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_homepage.*

class HomePageActivity : BaseActivity() {

    private val onNavItemSelected  = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
            R.id.home -> {
                showFragment(R.id.homeFragmentContainer, DataMonitorFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.list -> {
                showFragment(R.id.homeFragmentContainer, DataListFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.map -> {
                showFragment(R.id.homeFragmentContainer, MapFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.profile -> {
                showFragment(R.id.homeFragmentContainer, ProfileFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            else -> return@OnNavigationItemSelectedListener false
        }
    }


    override fun getLayoutResourceId(): Int = R.layout.activity_homepage

    override fun setupUi() {
        showFragment(R.id.homeFragmentContainer, DataMonitorFragment.newInstance())
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        bottomNavBar.setOnNavigationItemSelectedListener(onNavItemSelected)
    }


}
