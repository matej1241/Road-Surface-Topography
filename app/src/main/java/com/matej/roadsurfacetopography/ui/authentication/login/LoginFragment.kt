package com.matej.roadsurfacetopography.ui.authentication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import org.koin.android.ext.android.inject

import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.RoadSurfaceTopography
import com.matej.roadsurfacetopography.common.showFragment
import com.matej.roadsurfacetopography.model.UserData
import com.matej.roadsurfacetopography.ui.authentication.registration.RegistrationFragment
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import com.matej.roadsurfacetopography.ui.homePage.HomePageActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment(), LoginContract.View {

    private val presenter: LoginContract.Presenter by inject()

    override fun getLayoutResourceId(): Int = R.layout.fragment_login

    override fun setupUi() {
        loginProgress.visibility = View.GONE
        presenter.setView(this)
        if(presenter.getCurrentUser() != "null"){
            startActivity(HomePageActivity::class.java)
        }
    }

    override fun setOnClickListeners() {
        loginButton.setOnClickListener { onLoginClicked() }
        createNewAccountText.setOnClickListener { onCreateAccountClicked() }
    }

    private fun onLoginClicked() {
        loginProgress.visibility = View.VISIBLE
        presenter.onLoginClicked(
            UserData(
                email = authEmail.text.toString(),
                password = authPassword.text.toString()
            )
        )
    }

    private fun onCreateAccountClicked() {
        activity?.showFragment(R.id.authFragmentContainer, RegistrationFragment.newInstance())
    }

    override fun onLoginSuccessful() {
        loginProgress.visibility = View.GONE
        startActivity(HomePageActivity::class.java)
    }

    override fun onLoginFailed() {
        loginProgress.visibility = View.GONE
        Toast.makeText(RoadSurfaceTopography.instance, "Failed to log in", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): Fragment {
            return LoginFragment()
        }
    }
}
