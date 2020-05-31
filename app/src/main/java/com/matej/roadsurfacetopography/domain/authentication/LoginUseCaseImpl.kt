package com.matej.roadsurfacetopography.domain.authentication

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.matej.roadsurfacetopography.common.ErrorLambda
import com.matej.roadsurfacetopography.common.SuccessLambda
import com.matej.roadsurfacetopography.model.UserData

class LoginUseCaseImpl(private val auth: FirebaseAuth): LoginUseCase {
    override fun execute(body: UserData, onSuccess: SuccessLambda<Task<AuthResult>>, onFailure: ErrorLambda<Task<AuthResult>>) {
        auth.signInWithEmailAndPassword(body.email, body.password).addOnCompleteListener {
            if (it.isComplete && it.isSuccessful){
                it.run(onSuccess)
            }else{
                it.run(onFailure)
            }
        }
    }
}