package com.example.scaffoldcompose.item_views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClocksViewModel @Inject constructor() : ViewModel() {
    private var _clocks = MutableStateFlow<List<Clock>>(emptyList())
    val clocks = _clocks.asStateFlow()

    init {
        getTimes()
        moveClocks()
    }

    private fun moveClocks() = viewModelScope.launch {
        while (true) {
            delay(1000L)
            _clocks.value = _clocks.value.map {
                if (it.isVisible) {
                    it.copy(time = it.time + 1000L)
                } else {
                    it
                }
            }
        }
    }

    private fun getTimes() {
        _clocks.value = buildList {
            for (i in 0 until 20) {
                add(Clock(time = 0L, isVisible = true, name = "Clock $i"))
            }
        }
    }

    fun addTime() {
        _clocks.value = _clocks.value + buildList {
            for (i in 0 until 8) {
                add(Clock(0L, isVisible = false, name = "Clock ${_clocks.value.size + i}"))
            }
        }
    }

    fun setVisible(clock: Clock) {
        _clocks.value = _clocks.value.map {
            if (it.name == clock.name) {
                it.copy(isVisible = true)
            } else {
                it
            }
        }
    }
}