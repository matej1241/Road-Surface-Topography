package com.matej.roadsurfacetopography.presentation

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.matej.roadsurfacetopography.domain.authentication.RegistrationUseCase
import com.matej.roadsurfacetopography.model.UserData
import com.matej.roadsurfacetopography.ui.authentication.registration.RegistrationContract

class RegistrationPresenter(private val registerUseCase: RegistrationUseCase): RegistrationContract.Presenter {

    private lateinit var view: RegistrationContract.View

    override fun setView(view: RegistrationContract.View) {
        this.view = view
    }

    override fun onRegisterClicked(user: UserData) {
        registerUseCase.execute(user, ::onRegisterOkResponse, ::onRegisterFailedResponse)
    }

    private fun onRegisterOkResponse(response: Task<AuthResult>) = view.onRegisterSuccessful()

    private fun onRegisterFailedResponse(response: Task<AuthResult>) = view.onRegisterFailed()
}