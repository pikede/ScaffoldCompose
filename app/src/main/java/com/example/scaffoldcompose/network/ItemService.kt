package com.example.scaffoldcompose.network

import com.example.scaffoldcompose.models.ItemsResponse
import com.example.scaffoldcompose.models.Locations
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ItemService {

    @GET("/*todo add the endpoint here*/") // todo change the endpoint here
    suspend fun fetchItems(): List<ItemsResponse>

    @GET("location/{id}")
    suspend fun getLocations(@Path(dummyString) id: Int): Locations

    @POST("items}")
    suspend fun updateItems(
        @Header(dummyString) token: String,
        @Body item: ItemsResponse
    ): ItemsResponse
}

const val dummyString = "dummy content"