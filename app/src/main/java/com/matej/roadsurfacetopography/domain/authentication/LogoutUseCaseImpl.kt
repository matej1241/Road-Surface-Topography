package com.matej.roadsurfacetopography.domain.authentication

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.matej.roadsurfacetopography.common.ErrorLambda
import com.matej.roadsurfacetopography.common.LogoutSuccess
import com.matej.roadsurfacetopography.common.SuccessLambda
import com.matej.roadsurfacetopography.model.UserData
import com.matej.roadsurfacetopography.persistance.firebaseRepository.FirebaseRepository

class LogoutUseCaseImpl(private val firebaseRepository: FirebaseRepository): LogoutUseCase {
    override fun execute(onSuccess: LogoutSuccess) {
        firebaseRepository.getAuth().signOut()
        run(onSuccess)
    }
}