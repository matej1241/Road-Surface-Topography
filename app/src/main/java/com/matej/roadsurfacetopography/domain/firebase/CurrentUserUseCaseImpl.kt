package com.matej.roadsurfacetopography.domain.firebase

import com.google.firebase.auth.FirebaseAuth

class CurrentUserUseCaseImpl(private val auth: FirebaseAuth): CurrentUserUseCase {
    override fun execute(): String {
        val currentUser = auth.currentUser
        when(currentUser?.displayName){
            null -> return currentUser?.email.toString()
            else -> return currentUser.displayName.toString()
        }
    }
}