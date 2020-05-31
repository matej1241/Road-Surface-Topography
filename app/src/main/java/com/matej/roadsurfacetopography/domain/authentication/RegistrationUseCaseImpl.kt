package com.matej.roadsurfacetopography.domain.authentication

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.matej.roadsurfacetopography.common.ErrorLambda
import com.matej.roadsurfacetopography.common.SuccessLambda
import com.matej.roadsurfacetopography.model.UserData

class RegistrationUseCaseImpl (private val auth: FirebaseAuth): RegistrationUseCase {
    override fun execute(body: UserData, onSuccess: SuccessLambda<Task<AuthResult>>, onFailure: ErrorLambda<Task<AuthResult>>){
        auth.createUserWithEmailAndPassword(body.email, body.password).addOnCompleteListener {
            if (it.isComplete && it.isSuccessful){
                auth.currentUser?.updateProfile(UserProfileChangeRequest
                    .Builder()
                    .setDisplayName(body.username)
                    .build())
                it.run(onSuccess)
            }else{
                it.run(onFailure)
            }
        }
    }
}