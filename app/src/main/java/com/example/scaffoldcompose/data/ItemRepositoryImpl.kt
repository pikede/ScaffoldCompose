package com.example.scaffoldcompose.data

import android.util.Log
import com.example.scaffoldcompose.domain.ItemRepository
import com.example.scaffoldcompose.models.EquitiesItem
import com.example.scaffoldcompose.models.Locations
import com.example.scaffoldcompose.network.ItemService
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemService: ItemService,
) : ItemRepository {

    override suspend fun getItemsResponse(): List<EquitiesItem> {
        return runCatching { itemService.fetchItems() }
                .onSuccess { it }
                .onFailure { it.logError("${this@ItemRepositoryImpl::class.java} Error getting equities") }
                .getOrThrow()
    }

    private fun Throwable.logError(message: String) {
        Log.e("::logged", "failed in ${this@ItemRepositoryImpl::class.java} $message $this")
    }
}

