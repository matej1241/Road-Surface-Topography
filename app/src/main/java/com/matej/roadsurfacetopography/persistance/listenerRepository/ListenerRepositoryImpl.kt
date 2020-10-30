package com.matej.roadsurfacetopography.persistance.listenerRepository

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.matej.roadsurfacetopography.common.onSwipeToDelete
import com.matej.roadsurfacetopography.common.onSwipeToSave

class ListenerRepositoryImpl:  ListenerRepository{

    override fun setOnSwipeToDeleteListener(onSwipeToDelete: onSwipeToDelete): ItemTouchHelper.SimpleCallback{
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                onSwipeToDelete(position)
            }
        }
    }

    override fun setOnSwipeToSaveListener(onSwipeToSave: onSwipeToSave): ItemTouchHelper.SimpleCallback{
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                onSwipeToSave(position)
            }
        }
    }
}
