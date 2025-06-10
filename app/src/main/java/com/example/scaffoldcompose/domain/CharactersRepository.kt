package com.example.scaffoldcompose.domain

import com.example.scaffoldcompose.models.Locations
import com.example.scaffoldcompose.models.Result

interface CharactersRepository {
    suspend fun getCharacters(): List<Result>
    suspend fun getLocations(id: Int): Locations
}