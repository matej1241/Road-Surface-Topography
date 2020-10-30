package com.matej.roadsurfacetopography.persistance.firebaseRepository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

interface FirebaseRepository {
    fun getAuth(): FirebaseAuth
    fun getFireStore() : FirebaseFirestore
}