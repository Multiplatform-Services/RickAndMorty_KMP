package com.rickandmorty.kmp.presentation.screens.detail

import co.touchlab.kermit.Logger
import com.rickandmorty.kmp.domain.GetCharacterUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class CharacterDetailViewModel(
    private val id: Int,
    private val getCharacter: GetCharacterUseCase,
    private val logger: Logger,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logger.e(throwable) { "⚠️ Error fetching character: ${throwable.message}" }
        _characterListState.update { state ->
            state.copy(
                isLoading = false,
                error = "⚠️ Error fetching character: ${throwable.message}"
            )
        }
    }
    private val _characterListState = MutableStateFlow(CharacterDetailState())
    val characterListState: StateFlow<CharacterDetailState> get() = _characterListState

    init {
        viewModelScope.launch { fetchCharacter(id) }
    }

    private fun fetchCharacter(id: Int) {
        _characterListState.update { it.copy(isLoading = true) }
        logger.i { "🔍 Fetching characters from GetAllCharactersUseCase..." }
        viewModelScope.launch(exceptionHandler) {
            val character = getCharacter(id)
            logger.i { "📄 Character list fetched successfully" }
            _characterListState.update { it.copy(isLoading = false, character = character) }
            logger.i { "📄 Character list updated successfully" }
        }
    }
}
