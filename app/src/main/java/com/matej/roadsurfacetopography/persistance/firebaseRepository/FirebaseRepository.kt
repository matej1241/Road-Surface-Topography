package com.matej.roadsurfacetopography.persistance.firebaseRepository

import com.google.firebase.auth.FirebaseAuth

interface FirebaseRepository {
    fun getAuth(): FirebaseAuth
}