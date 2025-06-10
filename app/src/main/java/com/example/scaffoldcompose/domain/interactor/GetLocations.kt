package com.example.scaffoldcompose.domain.interactor

import com.example.scaffoldcompose.domain.CharactersRepository
import com.example.scaffoldcompose.models.Locations
import javax.inject.Inject

class GetLocations @Inject constructor(private val repository: CharactersRepository) {

    suspend operator fun invoke(id: Int): Locations {
        return repository.getLocations(id)
    }
}
