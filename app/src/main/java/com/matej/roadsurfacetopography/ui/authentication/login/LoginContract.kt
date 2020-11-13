package com.matej.roadsurfacetopography.ui.authentication.login

import com.matej.roadsurfacetopography.domain.firebase.CurrentUserUseCase
import com.matej.roadsurfacetopography.model.UserData

interface LoginContract {

    interface View{
        fun onLoginSuccessful()
        fun onLoginFailed()
    }

    interface Presenter{
        fun setView(view: View)
        fun onLoginClicked(userDataRequest: UserData)
        fun getCurrentUser(): String
    }
}