package com.example.scaffoldcompose.item_views

import androidx.compose.runtime.Immutable
import com.example.scaffoldcompose.models.EquitiesItem

@Immutable
data class ItemsViewState(
    val isLoading: Boolean = false,
    val itemNames: List<EquitiesItem> = emptyList(),
    val error: Throwable? = null,
) {
    companion object{
        val EMPTY = ItemsViewState()
    }
}