package com.matej.roadsurfacetopography.domain.firestore

import android.util.Log
import com.matej.roadsurfacetopography.common.onGetDataFailed
import com.matej.roadsurfacetopography.common.onGetDataSuccessful
import com.matej.roadsurfacetopography.common.onSaveFailed
import com.matej.roadsurfacetopography.common.onSaveSuccessful
import com.matej.roadsurfacetopography.persistance.firebaseRepository.FirebaseRepository

class FirestoreUseCaseImpl(private val firebaseRepository: FirebaseRepository): FirestoreUseCase {

    override fun saveData(data: HashMap<String, out Any>, onSaveSuccessful: onSaveSuccessful, onSaveFailed: onSaveFailed) {
        firebaseRepository.getFireStore().collection("sensor_data")
            .add(data)
            .addOnSuccessListener { run(onSaveSuccessful) }
            .addOnFailureListener { run(onSaveFailed) }
    }

    override fun getData(user: String, onGetDataSuccessful: onGetDataSuccessful, onGetDataFailed: onGetDataFailed) {
        firebaseRepository.getFireStore().collection("sensor_data")
            .whereEqualTo("user", user)
            .get()
            .addOnSuccessListener { run { onGetDataSuccessful(it) } }
            .addOnFailureListener { run(onGetDataFailed) }
    }

    override fun getAllData(onGetDataSuccessful: onGetDataSuccessful, onGetDataFailed: onGetDataFailed) {
        firebaseRepository.getFireStore().collection("sensor_data")
            .get()
            .addOnSuccessListener { run { onGetDataSuccessful(it) } }
            .addOnFailureListener { run(onGetDataFailed) }
    }

    override fun deleteData(dataId: String) {
        firebaseRepository.getFireStore().collection("sensor_data")
            .document(dataId)
            .delete()
    }

}