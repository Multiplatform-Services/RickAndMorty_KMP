package com.rickandmorty.kmp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rickandmorty.kmp.domain.detail.CharacterDetailViewModel
import com.rickandmorty.kmp.domain.toolbar.ToolbarState
import moe.tlaster.precompose.koin.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CharacterDetailScreen(
    id: Int,
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
    updateToolbarState: (ToolbarState) -> Unit
) {
    val viewModel = koinViewModel(CharacterDetailViewModel::class) { parametersOf(id) }
    val state by viewModel.characterListState.collectAsState()
    LaunchedEffect(state.character?.name) {
        updateToolbarState(
            ToolbarState(
                title = state.character?.name ?: "Details",
                showBackButton = true,
                onBack = goBack
            )
        )
    }

    Box(modifier, contentAlignment = Alignment.Center) {
        Text(state.character?.toString() ?: "Error happened")
        if (state.isLoading) CircularProgressIndicator()
    }
}
