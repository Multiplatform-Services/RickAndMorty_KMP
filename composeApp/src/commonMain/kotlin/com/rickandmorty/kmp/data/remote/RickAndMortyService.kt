package com.rickandmorty.kmp.data.remote

import co.touchlab.kermit.Logger
import com.rickandmorty.kmp.data.entity.Character
import com.rickandmorty.kmp.data.remote.response.CharacterListResponse
import com.rickandmorty.kmp.data.remote.response.CharacterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

class RickAndMortyService(private val client: HttpClient, private val logger: Logger) {

    suspend fun fetchCharacters(): List<Character> {
        val response = fetchFromApi<CharacterListResponse>("character")
        if (response == null) {
            logger.i { "📄 No Characters found" }
            return emptyList()
        }
        logger.i { "📄 Characters response data: ${response.info}" }
        return response.results
            .map { character ->
                Character(
                    id = character.id,
                    name = character.name,
                    status = character.status,
                    image = character.image
                )
            }
            .also { logger.i { "📝 Character list: $it" } }
    }

    suspend fun fetchCharacter(id: Int): Character {
        val endpoint = "character/$id"
        val response = fetchFromApi<CharacterResponse>(endpoint) ?: error("Character not found")

        return Character(
            id = response.id,
            name = response.name,
            status = response.status,
            image = response.image
        ).also {
            logger.i { "📝 Character: $it" }
        }
    }

    private suspend inline fun <reified T> fetchFromApi(endpoint: String): T? {
        logger.i { "🌐 Fetching data from API endpoint: $endpoint..." }
        val response: HttpResponse = client.get(endpoint)
        return if (response.status.isSuccess()) {
            logger.i { "✅ Response was successful: ${response.status}" }
            response.body<T>().also { logger.i { "📄 Response data: $it" } }
        } else {
            logger.e { "❌ Response failed: ${response.status}" }
            null
        }
    }
}
