package com.matej.roadsurfacetopography.ui.authentication.registration

import com.matej.roadsurfacetopography.model.UserData

interface RegistrationContract {

    interface View{
        fun onRegisterSuccessful()
        fun onRegisterFailed()
    }

    interface Presenter{
        fun setView(view: View)
        fun onRegisterClicked(user: UserData)
    }
}