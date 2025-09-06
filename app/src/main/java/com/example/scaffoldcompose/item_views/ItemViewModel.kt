package com.example.scaffoldcompose.item_views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scaffoldcompose.domain.ItemRepository
import com.example.scaffoldcompose.models.EquitiesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(private val itemRepository: ItemRepository) :
    ViewModel() { // todo use usecase here instead
    private val default = ItemsViewState.EMPTY
    private val isLoadingState = MutableStateFlow(default.isLoading)
    private val errorState = MutableStateFlow(default.error)
    private val itemsViewState = MutableStateFlow(default.itemNames)
    private var originalList = mutableListOf<EquitiesItem>()

    val state = combine(
        isLoadingState,
        itemsViewState,
        errorState,
        ::ItemsViewState
    ).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        default
    )

    init {
        getItemNames()
    }

    private fun getItemNames() = viewModelScope.launch {
        isLoadingState.value = true
        runCatching { itemRepository.getItemsResponse() }
            .onSuccess {
                originalList = it.sortedBy { it.current_price }.toMutableList()
                itemsViewState.value = originalList
            }
            .onFailure { errorState.value = it }
        isLoadingState.value = false
    }

    fun searchFor(string: String) {
        val filteredItems =
            originalList.filter { it.name.contains(string, ignoreCase = true) }
                .sortedBy { it.current_price }
        itemsViewState.value = if (filteredItems.isNotEmpty()) filteredItems else originalList
    }

    fun getMore() = viewModelScope.launch {
        isLoadingState.value = true
        delay(2000)
        itemsViewState.value = buildList {
            addAll(itemsViewState.value)
            for (i in 0..20) {
                originalList.map {
                    add(it.copy(name = "${it.name} ${i + 1}"))
                }
            }
            val lastItem = originalList.last().copy(name = "last item++")
            add(lastItem)
        }
        isLoadingState.value = false
    }
}
