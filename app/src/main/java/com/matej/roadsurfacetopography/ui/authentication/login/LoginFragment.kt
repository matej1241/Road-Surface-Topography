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
import com.matej.roadsurfacetopography.model.UserData
import com.matej.roadsurfacetopography.ui.base.BaseFragment
import com.matej.roadsurfacetopography.ui.homePage.HomePageActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment(), LoginContract.View {

    private val presenter: LoginContract.Presenter by inject<LoginContract.Presenter>()

    override fun getLayoutResourceId(): Int = R.layout.fragment_login

    override fun setupUi() {
        presenter.setView(this)
    }

    override fun setOnClickListeners() {
        loginButton.setOnClickListener { onLoginClicked() }
    }

    private fun onLoginClicked() {
        presenter.onLoginClicked(
            UserData(
                email = authEmail.text.toString(),
                password = authPassword.text.toString()
            )
        )
    }

    override fun onLoginSuccessful() {
        startActivity(HomePageActivity::class.java)
    }

    override fun onLoginFailed() {
        Toast.makeText(RoadSurfaceTopography.instance, "Pogreska", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): Fragment {
            return LoginFragment()
        }
    }
}
