package com.matej.roadsurfacetopography.common

import com.google.firebase.firestore.QuerySnapshot
import com.matej.roadsurfacetopography.model.SensorData
import com.matej.roadsurfacetopography.model.SensorDataDb

typealias SuccessLambda<T> = (T) -> Unit
typealias ErrorLambda<T> = (T) -> Unit
typealias onLocationClickedListener = (data: SensorDataDb) -> Unit
typealias onSwipeToDelete = (position: Int) -> Unit
typealias onSwipeToSave = (position: Int) -> Unit
typealias onSaveSuccessful = () -> Unit
typealias onSaveFailed = () -> Unit
typealias onGetDataSuccessful = (data: QuerySnapshot) -> Unit
typealias onGetDataFailed = () -> Unit