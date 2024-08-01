package com.rickandmorty.kmp.domain.list

import co.touchlab.kermit.Logger
import com.rickandmorty.kmp.data.usecase.GetAllCharactersUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class CharacterListViewModel(
    private val getAllCharacters: GetAllCharactersUseCase,
    private val logger: Logger,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logger.e(throwable) { "⚠️ Error fetching characters: ${throwable.message}" }
        _characterListState.update { state ->
            state.copy(
                isLoading = false,
                error = "⚠️ Error fetching characters: ${throwable.message}"
            )
        }
    }
    private val _characterListState = MutableStateFlow(CharacterListState())
    val characterListState: StateFlow<CharacterListState> get() = _characterListState

    init {
        fetchAllCharacters()
    }

    private fun fetchAllCharacters() {
        _characterListState.update { it.copy(isLoading = true) }
        logger.i { "🔍 Fetching characters from GetAllCharactersUseCase..." }
        viewModelScope.launch(exceptionHandler) {
            val characterList = getAllCharacters()
            logger.i { "📄 Character list fetched successfully" }
            _characterListState.update { it.copy(isLoading = false, characters = characterList) }
            logger.i { "📄 Character list updated successfully" }
        }
    }
}