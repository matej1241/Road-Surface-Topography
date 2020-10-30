package com.matej.roadsurfacetopography.persistance.firebaseRepository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseRepositoryImpl(private val auth: FirebaseAuth, private val firestore: FirebaseFirestore): FirebaseRepository {
    override fun getAuth(): FirebaseAuth = auth
    override fun getFireStore(): FirebaseFirestore = firestore
}