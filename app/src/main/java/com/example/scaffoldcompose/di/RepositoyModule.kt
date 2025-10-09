package com.example.scaffoldcompose.di

import com.example.scaffoldcompose.data.ItemRepositoryImpl
import com.example.scaffoldcompose.domain.CharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun createItemRepository(itemRepositoryImpl: ItemRepositoryImpl): CharactersRepository
}