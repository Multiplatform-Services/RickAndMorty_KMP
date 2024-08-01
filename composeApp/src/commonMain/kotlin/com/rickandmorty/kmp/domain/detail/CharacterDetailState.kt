package com.rickandmorty.kmp.domain.detail

import com.rickandmorty.kmp.data.entity.CharacterEntity

data class CharacterDetailState(
    val isLoading: Boolean = true,
    val character: CharacterEntity? = null,
    val error: String = ""
)
