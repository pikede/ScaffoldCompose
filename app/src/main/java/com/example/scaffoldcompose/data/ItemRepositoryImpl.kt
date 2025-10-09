package com.example.scaffoldcompose.data

import android.util.Log
import com.example.scaffoldcompose.domain.CharactersRepository
import com.example.scaffoldcompose.models.Locations
import com.example.scaffoldcompose.models.Result
import com.example.scaffoldcompose.network.ItemService
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemService: ItemService,
) :
    CharactersRepository {

    override suspend fun getCharacters(): List<Result> {
        return runCatching { itemService.fetchCharactersCoroutine() }
            .onSuccess { it }
            .onFailure {
                it.logError("${this@ItemRepositoryImpl::class.java} Error getting Item Names")
                it
            }
            .getOrThrow().results
    }

    override suspend fun getLocations(id: Int): Locations {
        return runCatching { itemService.getLocations(id) }
            .onSuccess { it }
            .onFailure {
                it.logError("${this@ItemRepositoryImpl::class.java} Error getting Locations")
                it
            }
            .getOrThrow()
    }


    private fun Throwable.logError(message: String) {
        Log.e("::logged", "failed in ${this@ItemRepositoryImpl::class.java} $message $this")
    }
}