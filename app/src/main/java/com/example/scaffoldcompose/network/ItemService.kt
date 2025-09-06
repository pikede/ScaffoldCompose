package com.example.scaffoldcompose.network

import com.example.scaffoldcompose.models.EquitiesItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ItemService {

    @GET("dns-mcdaid/b248c852b743ad960616bac50409f0f0/raw/6921812bfb76c1bea7868385adf62b7f447048ce/instruments.json")
    suspend fun fetchItems(): List<EquitiesItem>

    // start region examples
    @GET("location/{id}/stuff/{stuffId}")
    suspend fun getLocations(
        @Header(dummyString) token: String = dummyString,
        @Path("id") id: Int,
        @Path("stuffId") stuffId: Int,
        @QueryMap queryMap: Map<String, String> = emptyMap(),
        @Query("queryString") queryFilter: String = dummyString,
    ): EquitiesItem

    @POST("items}")
    suspend fun updateItems(
        @Header(dummyString) token: String,
        @Body item: EquitiesItem,
    ): EquitiesItem

    // end region examples
}

const val dummyString = "dummy content"

