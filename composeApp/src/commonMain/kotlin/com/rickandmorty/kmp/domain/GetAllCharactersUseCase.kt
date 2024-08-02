package com.rickandmorty.kmp.domain

import com.rickandmorty.kmp.data.RickAndMortyRepo

class GetAllCharactersUseCase(private val repo: RickAndMortyRepo) {

    suspend operator fun invoke() = repo.characters()
}
