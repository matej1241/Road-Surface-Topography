package com.matej.roadsurfacetopography.ui.authentication.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.RoadSurfaceTopography
import com.matej.roadsurfacetopography.common.showFragment
import com.matej.roadsurfacetopography.model.UserData
import com.matej.roadsurfacetopography.ui.authentication.login.LoginFragment
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import com.matej.roadsurfacetopography.ui.homePage.HomePageActivity
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_registration.*
import org.koin.android.ext.android.inject

class RegistrationFragment : BaseFragment(), RegistrationContract.View {

    private val presenter: RegistrationContract.Presenter by inject<RegistrationContract.Presenter>()

    override fun getLayoutResourceId(): Int = R.layout.fragment_registration

    override fun setupUi() {
        registerProgress.visibility = View.GONE
        presenter.setView(this)
    }

    override fun setOnClickListeners() {
        registerButton.setOnClickListener { onRegisterClicked() }
        loginInsteadText.setOnClickListener { onLoginInsteadClicked() }
    }

    private fun onRegisterClicked() {
        registerProgress.visibility = View.VISIBLE
        presenter.onRegisterClicked(
            UserData(
                regEmail.text.toString(),
                regUsername.text.toString(),
                regPassword.text.toString()
            )
        )
    }

    private fun onLoginInsteadClicked() {
        activity?.showFragment(R.id.authFragmentContainer, LoginFragment.newInstance())
    }

    override fun onRegisterSuccessful() {
        registerProgress.visibility = View.GONE
        startActivity(HomePageActivity::class.java)
    }

    override fun onRegisterFailed() {
        registerProgress.visibility = View.GONE
        Toast.makeText(RoadSurfaceTopography.instance, "Failed to register", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): Fragment {
            return RegistrationFragment()
        }
    }
}
