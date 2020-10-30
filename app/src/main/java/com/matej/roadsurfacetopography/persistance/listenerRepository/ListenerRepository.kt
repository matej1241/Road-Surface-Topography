package com.matej.roadsurfacetopography.persistance.listenerRepository

import androidx.recyclerview.widget.ItemTouchHelper
import com.matej.roadsurfacetopography.common.onSwipeToDelete

interface ListenerRepository {
    fun setOnSwipeToDeleteListener(onSwipeToDelete: onSwipeToDelete): ItemTouchHelper.SimpleCallback
    fun setOnSwipeToSaveListener(onSwipeToDelete: onSwipeToDelete): ItemTouchHelper.SimpleCallback
}