package com.matej.roadsurfacetopography.domain.firestore

import com.matej.roadsurfacetopography.common.onGetDataFailed
import com.matej.roadsurfacetopography.common.onGetDataSuccessful
import com.matej.roadsurfacetopography.common.onSaveFailed
import com.matej.roadsurfacetopography.common.onSaveSuccessful

interface FirestoreUseCase {

    fun saveData(data: HashMap<String, out Any>, onSaveSuccessful: onSaveSuccessful, onSaveFailed: onSaveFailed)

    fun getData(user: String, onGetDataSuccessful: onGetDataSuccessful, onGetDataFailed: onGetDataFailed)
}