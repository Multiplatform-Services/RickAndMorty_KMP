package com.rickandmorty.kmp.di

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import com.rickandmorty.kmp.data.RickAndMortyRepo
import com.rickandmorty.kmp.data.remote.RickAndMortyService
import com.rickandmorty.kmp.data.usecase.GetAllCharactersUseCase
import com.rickandmorty.kmp.data.usecase.GetCharacterUseCase
import com.rickandmorty.kmp.domain.detail.CharacterDetailViewModel
import com.rickandmorty.kmp.domain.list.CharacterListViewModel
import com.rickandmorty.kmp.util.getLoggerWithTag
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule = module {
    includes(platformModule)
    common()
    single { RickAndMortyService(get(), getLoggerWithTag("RickAndMortyService")) }
    single { RickAndMortyRepo(get(), getLoggerWithTag("RickAndMortyRepo")) }
    useCases()
    viewModels()
}

private fun Module.common() {
    single {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }
    single {
        HttpClient(get<HttpClientEngine>()) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "rickandmortyapi.com"
                    path("api/")
                }
                contentType(ContentType.Application.Json)
            }
            expectSuccess = true
            install(ContentNegotiation) {
                json(get<Json>())
            }
        }
    }
    factory { params ->
        Logger(
            config = StaticConfig(logWriterList = logWriters),
            tag = params[0] ?: "RickAndMorty"
        )
    }
}

private fun Module.viewModels() {
    factory { CharacterListViewModel(get(), getLoggerWithTag("CharacterListViewModel")) }
    factory { (id: Int) ->
        CharacterDetailViewModel(id, get(), getLoggerWithTag("CharacterDetailViewModel"))
    }
}

private fun Module.useCases() {
    single { GetAllCharactersUseCase(get()) }
    single { GetCharacterUseCase(get()) }
}

expect val logWriters: List<LogWriter>
expect val platformModule: Module
