package com.matej.roadsurfacetopography.ui.homePage.profile

import android.util.Log
import androidx.fragment.app.Fragment

import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.model.SensorDataDb
import com.matej.roadsurfacetopography.ui.authentication.AuthenticationActivity
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import com.matej.roadsurfacetopography.ui.homePage.HomePageActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject


class ProfileFragment : BaseFragment(), ProfileContract.View {

    private val presenter: ProfileContract.Presenter by inject()
    private var localData: List<SensorDataDb> = mutableListOf()
    private var remoteData: List<SensorDataDb> = mutableListOf()

    override fun getLayoutResourceId(): Int = R.layout.fragment_profile

    override fun setupUi() {
        presenter.setView(this)
        (activity as HomePageActivity?)?.supportActionBar?.hide()
        userNameText.text = presenter.getCurrentUser()
        presenter.getSensorFsData()
        setLocalDataStatistic()
    }

    override fun setOnClickListeners() {
        logOutText.setOnClickListener { onLogoutClicked() }
    }

    private fun onLogoutClicked() {
        presenter.signOut()
    }

    private fun setLocalDataStatistic(){
        localData = presenter.getSensorData()
        val bumpClassCount = getBumpClassCount(localData)
        localDataIregFoundText.text = "Irregularities found: ${localData.size.toString()}"
        localDataSmallFoundText.text = "Small: ${bumpClassCount[0].toString()}"
        localDataMedSmallFoundText.text = "Medium-small: ${bumpClassCount[1].toString()}"
        localDataMedFoundText.text = "Medium: ${bumpClassCount[2].toString()}"
        localDataMedBigFoundText.text = "Medium-big: ${bumpClassCount[3].toString()}"
        localDataBigFoundText.text = "Big: ${bumpClassCount[4].toString()}"
    }

    private fun setRemoteDataStatistic() {
        val bumpClassCount = getBumpClassCount(remoteData)
        remoteDataIregFoundText.text = "Irregularities found: ${remoteData.size.toString()}"
        remoteDataSmallFoundText.text = "Small: ${bumpClassCount[0].toString()}"
        remoteDataMedSmallFoundText.text = "Medium-small: ${bumpClassCount[1].toString()}"
        remoteDataMedFoundText.text = "Medium: ${bumpClassCount[2].toString()}"
        remoteDataMedBigFoundText.text = "Medium-big: ${bumpClassCount[3].toString()}"
        remoteDataBigFoundText.text = "Big: ${bumpClassCount[4].toString()}"
    }

    override fun onGetDataSuccessful(data: List<SensorDataDb>) {
        remoteData = data
        setRemoteDataStatistic()
    }

    override fun onGetDataFailed() {
        Log.d("+++", "Failed to get data")
    }

    override fun onSignOutSuccessful() {
        startActivity(AuthenticationActivity::class.java)
    }

    private fun getBumpClassCount(data: List<SensorDataDb>): Array<Int>{
        var small = 0
        var medSmall = 0
        var med = 0
        var medBig = 0
        var big = 0
        for(value in data){
            when(value.bumpType){
                1 -> small++
                2 -> medSmall++
                3 -> med++
                4 -> medBig++
                5 -> big++
            }
        }
        return arrayOf(small, medSmall, med, medBig, big)
    }

    companion object {
        fun newInstance(): Fragment {
            return ProfileFragment()
        }

    }
}
