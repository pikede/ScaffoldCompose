package com.example.scaffoldcompose.domain

import com.example.scaffoldcompose.models.EquitiesItem

interface ItemRepository {
    suspend fun getItemsResponse(): List<EquitiesItem>
}