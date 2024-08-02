package com.rickandmorty.kmp.domain

import com.rickandmorty.kmp.data.RickAndMortyRepo

class GetCharacterUseCase(private val repo: RickAndMortyRepo) {

    suspend operator fun invoke(id: Int) = repo.character(id)
}
