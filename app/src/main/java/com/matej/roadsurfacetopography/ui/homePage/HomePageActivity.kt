package com.matej.roadsurfacetopography.ui.homePage

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.RoadSurfaceTopography
import com.matej.roadsurfacetopography.common.showFragment
import com.matej.roadsurfacetopography.ui.base.BaseActivity
import com.matej.roadsurfacetopography.ui.homePage.dataList.DataListFragment
import com.matej.roadsurfacetopography.ui.homePage.dataMonitor.DataMonitorFragment
import com.matej.roadsurfacetopography.ui.homePage.map.MapFragment
import com.matej.roadsurfacetopography.ui.homePage.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_homepage.*

class HomePageActivity : BaseActivity() {

    private val RECORD_REQUEST_CODE = 101

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
        setupPermissions()
        setOnClickListeners()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() = ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), RECORD_REQUEST_CODE)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("+++", "Permission has been denied by user")
                } else {
                    Log.i("+++", "Permission has been granted by user")
                }
            }
        }
    }

    private fun setOnClickListeners() {
        bottomNavBar.setOnNavigationItemSelectedListener(onNavItemSelected)
    }

}
