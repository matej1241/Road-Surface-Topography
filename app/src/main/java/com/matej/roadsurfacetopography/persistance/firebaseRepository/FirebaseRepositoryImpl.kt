package com.matej.roadsurfacetopography.persistance.firebaseRepository

import com.google.firebase.auth.FirebaseAuth

class FirebaseRepositoryImpl(private val auth: FirebaseAuth): FirebaseRepository {
    override fun getAuth(): FirebaseAuth = auth
}