package com.example.scaffoldcompose.location

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.scaffoldcompose.DestinationRoutes
import com.example.scaffoldcompose.domain.interactor.GetLocations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLocations: GetLocations,
) : ViewModel() {
    private val locationID = savedStateHandle.toRoute<DestinationRoutes.Locations>().locationId
    private val locationIdState = MutableStateFlow(locationID)
    private val default = LocationsViewState.EMPTY
    private val isLoadingState = MutableStateFlow(default.isLoading)
    private val errorState = MutableStateFlow(default.error)
    private val locationsViewState = MutableStateFlow(default.locations)

    val state = combine(
        isLoadingState,
        locationIdState,
        locationsViewState,
        errorState,
        ::LocationsViewState
    ).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000), // state resets after 5 seconds when no subscribers are present
        default
    )

    init {
        getLocations(locationID)
    }

    private fun getLocations(id: Int) = viewModelScope.launch {
        isLoadingState.value = true
        locationIdState.value = id
        runCatching { getLocations.invoke(id) }
            .onSuccess { locationsViewState.value = it }
            .onFailure {
                errorState.value = it
            }
        isLoadingState.value = false
    }
}