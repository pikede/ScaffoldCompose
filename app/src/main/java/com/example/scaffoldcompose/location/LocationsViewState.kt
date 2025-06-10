package com.example.scaffoldcompose.location

import androidx.compose.runtime.Immutable
import com.example.scaffoldcompose.models.Locations

@Immutable
data class LocationsViewState(
    val isLoading: Boolean = false,
    val locationId: Int? = null,
    val locations: Locations? = null,
    val error: Throwable? = null,
) {
    companion object{
        val EMPTY = LocationsViewState()
    }

}