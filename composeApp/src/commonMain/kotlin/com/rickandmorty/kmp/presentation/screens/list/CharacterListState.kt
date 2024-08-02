package com.rickandmorty.kmp.presentation.screens.list

import com.rickandmorty.kmp.data.entity.Character

data class CharacterListState(
    val isLoading: Boolean = true,
    val characters: List<Character> = emptyList(),
    val error: String = ""
)
