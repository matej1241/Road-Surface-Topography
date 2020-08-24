package com.matej.roadsurfacetopography.ui.homePage.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.ui.base.BaseFragment

class MapFragment : BaseFragment() {

    override fun getLayoutResourceId(): Int = R.layout.fragment_map

    override fun setupUi() {
    }

    override fun setOnClickListeners() {
    }

    companion object {
        fun newInstance(): Fragment {
            return MapFragment()
        }
    }
}
