package com.matej.roadsurfacetopography

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.matej.roadsurfacetopography.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class RoadSurfaceTopography: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        startKoin {
            modules(listOf(
                databaseModule,
                firebaseModule,
                presentationModule,
                repositoryModule,
                domainModule
            ))
            androidContext(this@RoadSurfaceTopography)
        }
    }

    companion object{
        lateinit var instance: RoadSurfaceTopography
            private set
    }
}