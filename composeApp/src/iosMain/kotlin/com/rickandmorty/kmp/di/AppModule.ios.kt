package com.rickandmorty.kmp.di

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.NSLogWriter
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<HttpClientEngine> { Darwin.create() }
}

actual val logWriters: List<LogWriter> = listOf(NSLogWriter())
