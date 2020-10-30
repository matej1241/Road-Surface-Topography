package com.matej.roadsurfacetopography.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.matej.roadsurfacetopography.ui.homePage.dataList.DataListFragment
import com.matej.roadsurfacetopography.ui.homePage.dataList.DataListFsFragment

class DataListPagerAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(
        DataListFragment.newInstance(),
        DataListFsFragment.newInstance()
    )

    private val titles = arrayOf("Local data", "Firestore data")

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    override fun getCount(): Int = fragments.size
}