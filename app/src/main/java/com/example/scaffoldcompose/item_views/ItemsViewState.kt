package com.example.scaffoldcompose.item_views

import androidx.compose.runtime.Immutable
import com.example.scaffoldcompose.models.Result

@Immutable
data class ItemsViewState(
    val isLoading: Boolean = false,
    val itemNames: List<Result> = emptyList(),
    val error: Throwable? = null,
) {
    companion object{
        val EMPTY = ItemsViewState()
    }
}