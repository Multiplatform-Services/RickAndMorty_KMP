package com.rickandmorty.kmp.presentation.screens.detail

import com.rickandmorty.kmp.data.entity.Character

data class CharacterDetailState(
    val isLoading: Boolean = true,
    val character: Character? = null,
    val error: String = ""
)
