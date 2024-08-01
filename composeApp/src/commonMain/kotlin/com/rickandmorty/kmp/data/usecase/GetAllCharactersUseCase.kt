package com.rickandmorty.kmp.data.usecase

import com.rickandmorty.kmp.data.RickAndMortyRepo

class GetAllCharactersUseCase(private val repo: RickAndMortyRepo) {

    suspend operator fun invoke() = repo.characters()
}
