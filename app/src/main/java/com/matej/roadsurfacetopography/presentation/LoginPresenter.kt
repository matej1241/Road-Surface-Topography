package com.matej.roadsurfacetopography.presentation

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.matej.roadsurfacetopography.domain.authentication.LoginUseCase
import com.matej.roadsurfacetopography.domain.firebase.CurrentUserUseCase
import com.matej.roadsurfacetopography.domain.firebase.CurrentUserUseCaseImpl
import com.matej.roadsurfacetopography.model.UserData
import com.matej.roadsurfacetopography.ui.authentication.login.LoginContract

class LoginPresenter(
    private val loginUseCase: LoginUseCase,
    private val currentUserUseCase: CurrentUserUseCase
): LoginContract.Presenter {

    private lateinit var view: LoginContract.View

    override fun setView(view: LoginContract.View) {
        this.view = view
    }

    override fun onLoginClicked(userDataRequest: UserData) {
        loginUseCase.execute(userDataRequest, ::onLoginOkResponse, ::onLoginFailedResponse)
    }

    override fun getCurrentUser(): String = currentUserUseCase.execute()

    private fun onLoginOkResponse(response: Task<AuthResult>) = view.onLoginSuccessful()

    private fun onLoginFailedResponse(response: Task<AuthResult>) = view.onLoginFailed()
}