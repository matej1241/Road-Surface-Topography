package com.matej.roadsurfacetopography.di

import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val firebaseModule = module {
    factory<FirebaseAuth> { provideFirebaseAuth() }
}

fun provideFirebaseAuth(): FirebaseAuth{
    return FirebaseAuth.getInstance()
}