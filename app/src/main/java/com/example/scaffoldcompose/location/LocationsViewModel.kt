package com.example.scaffoldcompose.location

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.scaffoldcompose.DestinationRoutes
import com.example.scaffoldcompose.domain.interactor.GetLocations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
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
    private var _clocks = MutableStateFlow<List<Clock>>(mutableListOf())
    val clocks = _clocks.asStateFlow()

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
        initClocksValue()
        runClocks()
    }

    fun toggleClock(clock: Clock) = viewModelScope.launch {
        _clocks.value = _clocks.value.map {
            if (it.name == clock.name) {
                it.copy(isRunning = !it.isRunning)
            } else it
        }
    }

    private fun initClocksValue() {
        _clocks.value = buildList {
            for (i in 0 until 25) {
                add(Clock("Clock ${i + 1}", System.currentTimeMillis(), false))
            }
        }
    }

    private fun runClocks() = viewModelScope.launch {
        while (true) {
            delay(100L)
            _clocks.value = _clocks.value.map { currentClock ->
                if (currentClock.isRunning) {
                    println("clock time is ${currentClock.time}")
                    currentClock.copy(time = currentClock.time + 3110)
                } else {
                    currentClock
                }
            }
        }
    }
}