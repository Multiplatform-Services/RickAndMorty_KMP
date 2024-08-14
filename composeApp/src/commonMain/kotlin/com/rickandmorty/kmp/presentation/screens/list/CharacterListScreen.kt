package com.rickandmorty.kmp.presentation.screens.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rickandmorty.kmp.data.entity.Character
import com.rickandmorty.kmp.presentation.toolbar.ToolbarState
import com.seiko.imageloader.rememberImagePainter
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel

@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    goToCharacterDetail: (Character) -> Unit,
    updateToolbarState: (ToolbarState) -> Unit = {}
) {
    val viewModel = koinViewModel<CharacterListViewModel>()
    val state by viewModel.characterListState.collectAsState()
    LaunchedEffect(Unit) {
        updateToolbarState(ToolbarState("Characters"))
    }
    Box(modifier, contentAlignment = Alignment.Center) {
        CharacterList(state.characters, onCharacterClicked = goToCharacterDetail)
        if (state.isLoading) CircularProgressIndicator()
    }
}

@Composable
fun CharacterList(
    characters: List<Character>,
    modifier: Modifier = Modifier,
    onCharacterClicked: (Character) -> Unit = {},
) {

    val listState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val isFabVisible by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }
    Box{
        LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2), state = listState) {
            if (characters.isEmpty()) item { Text("No characters found.") }
            items(items = characters, key = { character -> character.id }) { character ->
                CharacterCard(character, onCharacterClicked)
            }
        }
        if (isFabVisible) {
            FloatingActionButton(
                onClick = { coroutineScope.launch { listState.animateScrollToItem(0) } },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                backgroundColor = Color.White
            ) {
                Icon(Icons.Default.Face, contentDescription = "Add Character")
            }
        }
    }
}

@Composable
fun CharacterCard(character: Character, onCharacterClicked: (Character) -> Unit) {
    val painter = rememberImagePainter(character.image)
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onCharacterClicked(character) },
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
    ) {
        Column {
            Image(
                painter = painter,
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = character.name,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Status: ${character.status}",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
