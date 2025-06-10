package com.example.scaffoldcompose.di

import com.example.scaffoldcompose.network.CoroutineDispatchers
import com.example.scaffoldcompose.network.ItemService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesItemService(retrofit: Retrofit): ItemService {
        return retrofit.create(ItemService::class.java)
    }

    @Provides
    @Singleton
    fun providesCoroutineDispatcher(): CoroutineDispatchers {
        return CoroutineDispatchers.default()
    }
}