package com.example.scaffoldcompose.domain.interactor

import com.example.scaffoldcompose.domain.CharactersRepository
import com.example.scaffoldcompose.models.Result
import javax.inject.Inject

class GetItemNames @Inject constructor(private val repository: CharactersRepository) {

    suspend operator fun invoke(): List<Result> {
        return repository.getCharacters()
    }
}
