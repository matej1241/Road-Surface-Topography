package com.matej.roadsurfacetopography.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val firebaseModule = module {
    factory<FirebaseAuth> { provideFirebaseAuth() }
    factory<FirebaseFirestore> { provideFirestore() }
}

fun provideFirebaseAuth(): FirebaseAuth{
    return FirebaseAuth.getInstance()
}

fun provideFirestore() : FirebaseFirestore = Firebase.firestore