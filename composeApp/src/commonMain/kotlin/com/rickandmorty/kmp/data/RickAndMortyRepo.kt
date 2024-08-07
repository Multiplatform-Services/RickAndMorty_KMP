package com.rickandmorty.kmp.data

import co.touchlab.kermit.Logger
import com.rickandmorty.kmp.data.entity.Character
import com.rickandmorty.kmp.data.remote.RickAndMortyService

class RickAndMortyRepo(private val service: RickAndMortyService, private val logger: Logger) {

    suspend fun characters(): List<Character> {
        logger.i { "🔍 Fetching characters from service..." }
        val characterList = service.fetchCharacters()
        logger.i { "📄 Character list: $characterList" }
        return characterList
    }

    suspend fun character(id: Int): Character {
        logger.i { "🔍 Fetching character with $id from service..." }
        val character = service.fetchCharacter(id)
        logger.i { "📄 Character found: $character" }
        return character
    }
}
