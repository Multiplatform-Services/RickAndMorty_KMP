package com.rickandmorty.kmp.util

import co.touchlab.kermit.Logger
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

/**
 * Get logger from koin.
 *
 * @param L Type of loggerKermitLogging.kt
 * @param tag unique identifier for each log
 * @return Instance of the Logger
 */
inline fun <reified L : Logger> Scope.getLoggerWithTag(tag: String): L = get(parameters = { parametersOf(tag) })

/**
 * Get logger from koin.
 *
 * @param L Type of logger
 * @param tag unique identifier for each log
 * @return Instance of the Logger
 */
inline fun <reified L : Logger> KoinComponent.getLoggerWithTag(tag: String): L = get(parameters = { parametersOf(tag) })
