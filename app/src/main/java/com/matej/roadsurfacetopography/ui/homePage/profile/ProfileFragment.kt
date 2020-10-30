package com.matej.roadsurfacetopography.ui.homePage.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import com.matej.roadsurfacetopography.ui.homePage.HomePageActivity


class ProfileFragment : BaseFragment() {

    override fun getLayoutResourceId(): Int = R.layout.fragment_profile

    override fun setupUi() {
        (activity as HomePageActivity?)?.supportActionBar?.show()
    }

    override fun setOnClickListeners() {
    }

    companion object {
        fun newInstance(): Fragment {
            return ProfileFragment()
        }
    }
}
