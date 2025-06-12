package com.example.scaffoldcompose.item_views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scaffoldcompose.domain.ItemRepository
import com.example.scaffoldcompose.models.ItemsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
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
    val anotherState = MutableStateFlow("")
    private val originalItems = getItems()

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
        itemsViewState.value = getItems()
        // todo get items here from the usecase/repository
//        runCatching { itemRepository.getItemsResponse() }
//            .onSuccess { itemsViewState.value = getItems() }
//            .onFailure { errorState.value = it }
        isLoadingState.value = false
    }

    fun searchFor(string: String) {
        val filteredItems = originalItems.filter { it.name.contains(string, ignoreCase = true) }
        itemsViewState.value = if (filteredItems.isNotEmpty()) filteredItems else originalItems
    }
}

private fun getItems(): List<ItemsResponse> {
    var id = 1
    val name = "John"
    val image = "Image"
    val item = buildList {
        repeat(10) {
            add(ItemsResponse(id.toString(), (name + id).toString(), (image + id).toString()))
            add(ItemsResponse(id.toString(), (name + id + id).toString(), (image + id).toString()))
            id++
        }
    }
    return item
}