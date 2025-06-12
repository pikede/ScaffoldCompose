package com.example.scaffoldcompose.domain

import com.example.scaffoldcompose.models.ItemsResponse
import com.example.scaffoldcompose.models.Locations

interface ItemRepository {
    suspend fun getItemsResponse(): List<ItemsResponse>
    suspend fun getLocations(id: Int): Locations
}