package com.example.scaffoldcompose.network

import com.example.scaffoldcompose.models.Characters
import com.example.scaffoldcompose.models.Locations
import retrofit2.http.GET
import retrofit2.http.Path

interface ItemService {

    @GET("character")
    suspend fun fetchCharactersCoroutine(): Characters

    @GET("location/{id}")
    suspend fun getLocations(@Path("id") id: Int): Locations
}