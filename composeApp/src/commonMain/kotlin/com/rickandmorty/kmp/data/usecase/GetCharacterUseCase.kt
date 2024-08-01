package com.rickandmorty.kmp.data.usecase

import com.rickandmorty.kmp.data.RickAndMortyRepo

class GetCharacterUseCase(private val repo: RickAndMortyRepo) {

    suspend operator fun invoke(id: Int) = repo.character(id)
}
