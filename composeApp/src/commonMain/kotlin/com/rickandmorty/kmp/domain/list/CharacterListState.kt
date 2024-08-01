package com.rickandmorty.kmp.domain.list

import com.rickandmorty.kmp.data.entity.CharacterEntity

data class CharacterListState(
    val isLoading: Boolean = true,
    val characters: List<CharacterEntity> = emptyList(),
    val error: String = ""
)
