package com.rickandmorty.kmp.di

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.LogcatWriter
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
    }

actual val logWriters: List<LogWriter> = listOf(LogcatWriter())
