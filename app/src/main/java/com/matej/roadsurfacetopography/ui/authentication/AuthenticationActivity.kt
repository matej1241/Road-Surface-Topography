package com.matej.roadsurfacetopography.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.matej.roadsurfacetopography.R
import com.matej.roadsurfacetopography.common.showFragment
import com.matej.roadsurfacetopography.ui.authentication.login.LoginFragment
import com.matej.roadsurfacetopography.ui.authentication.registration.RegistrationFragment
import com.matej.roadsurfacetopography.ui.base.BaseActivity

class AuthenticationActivity : BaseActivity() {
    override fun getLayoutResourceId(): Int = R.layout.activity_authentication

    override fun setupUi() {
        supportActionBar?.hide()
        showFragment(R.id.authFragmentContainer, LoginFragment.newInstance())
    }
}
