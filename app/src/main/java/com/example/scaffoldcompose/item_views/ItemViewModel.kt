package com.example.scaffoldcompose.item_views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scaffoldcompose.domain.interactor.GetItemNames
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(private val getItemNames: GetItemNames) : ViewModel() {
    private val default = ItemsViewState.EMPTY
    private val isLoadingState = MutableStateFlow(default.isLoading)
    private val errorState = MutableStateFlow(default.error)
    private val itemsViewState = MutableStateFlow(default.itemNames)

    val state = combine(
        isLoadingState,
        itemsViewState,
        errorState,
        ::ItemsViewState
    ).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000), // state resets after 5 seconds when no subscribers are present
        default
    )

    init {
        getItemNames()
    }

    private fun getItemNames() = viewModelScope.launch {
        isLoadingState.value = true
        runCatching { getItemNames.invoke() }
            .onSuccess { itemsViewState.value = it }
            .onFailure {
                errorState.value = it
            }
        isLoadingState.value = false
    }
}