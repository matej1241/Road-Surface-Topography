package com.matej.roadsurfacetopography.common

import com.matej.roadsurfacetopography.model.SensorData
import com.matej.roadsurfacetopography.model.SensorDataDb

typealias SuccessLambda<T> = (T) -> Unit
typealias ErrorLambda<T> = (T) -> Unit
typealias onLocationClickedListener = (data: SensorDataDb) -> Unit