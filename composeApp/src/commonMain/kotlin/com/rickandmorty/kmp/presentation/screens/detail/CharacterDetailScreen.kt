package com.rickandmorty.kmp.presentation.screens.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rickandmorty.kmp.presentation.toolbar.ToolbarState
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
        //Text(state.character?.toString() ?: "Error happened")
        LazyColumn {
            state.character?.let { character ->
                items(items = character.episode) { episode ->
                    ContentRow(episode)
                }
            }
        }
        if (state.isLoading) CircularProgressIndicator()
    }
}

@Composable
fun ContentRow(episode: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(100.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = episode)
        }
    }
}