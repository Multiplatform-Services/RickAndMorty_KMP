package com.rickandmorty.kmp.presentation

import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import co.touchlab.kermit.Logger
import com.rickandmorty.kmp.presentation.toolbar.ToolbarState
import com.rickandmorty.kmp.presentation.screens.detail.CharacterDetailScreen
import com.rickandmorty.kmp.presentation.screens.list.CharacterListScreen
import com.rickandmorty.kmp.util.Screen
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    var toolbarState by remember { mutableStateOf(ToolbarState(title = "Rick and Morty")) }
    PreComposeApp {
        val navigator = rememberNavigator()
        KoinContext {
            AppTheme {
                Scaffold(
                    topBar = { TopBar(toolbarState) },
                    bottomBar = { BottomNavigationBar(navigator) }
                ) { padding ->
                    NavHost(
                        modifier = Modifier.padding(padding),
                        navigator = navigator,
                        initialRoute = Screen.Home.route
                    ) {
                        group(Screen.Home.route, Screen.Home.characterList.route) {
                            characterList(
                                navigator = navigator,
                                updateToolbarState = {
                                    Logger.i("APP", message = { "Update toolbar state from List" })
                                    toolbarState = it
                                }
                            )
                            characterDetail(
                                navigator = navigator,
                                updateToolbarState = {
                                    Logger.i(
                                        "APP",
                                        message = { "Update toolbar state from Detail" })
                                    toolbarState = it
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(state: ToolbarState) {
    CenterAlignedTopAppBar(
        title = { Text(text = state.title) },
        navigationIcon = {
            if (state.showBackButton) {
                IconButton(onClick = { state.onBack?.invoke() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = Icons.AutoMirrored.Filled.ArrowBack.name
                    )
                }
            }
        },
        actions = {
            state.actions.forEach { action ->
                IconButton(onClick = action.onClick) {
                    Icon(imageVector = action.icon, contentDescription = null)
                }
            }
        }
    )
}

@Composable
private fun BottomNavigationBar(navigator: Navigator) {
    NavigationBar {
        val currentRoute by navigator.currentEntry.collectAsState(null)
        listOf(Screen.Home).forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.stringRes)) },
                selected = currentRoute?.route?.route?.contains(screen.route) ?: false,
                onClick = {
                    navigator.navigate(
                        route = screen.route,
                        options = NavOptions(
                            launchSingleTop = true, popUpTo = PopUpTo(screen.route)
                        )
                    )
                }
            )
        }
    }
}

private fun RouteBuilder.characterList(
    navigator: Navigator,
    updateToolbarState: (ToolbarState) -> Unit
) {
    scene(Screen.Home.characterList.route) {
        CharacterListScreen(
            modifier = Screen.defaultModifier(),
            goToCharacterDetail = { character ->
                navigator.navigate(Screen.Home.characterDetailRoute(character.id))
            },
            updateToolbarState = updateToolbarState
        )
    }
}

private fun RouteBuilder.characterDetail(
    navigator: Navigator,
    updateToolbarState: (ToolbarState) -> Unit
) {
    scene(
        route = Screen.Home.characterDetail.route,
        navTransition = NavTransition(
            createTransition = slideInVertically(initialOffsetY = { it }),
            destroyTransition = slideOutVertically(targetOffsetY = { it }),
            pauseTransition = scaleOut(targetScale = 0.9f),
            resumeTransition = scaleIn(initialScale = 0.9f),
            exitTargetContentZIndex = 1f,
        ),
    ) { backStackEntry ->
        backStackEntry.path<Int>("id")?.let {
            CharacterDetailScreen(
                modifier = Screen.defaultModifier(),
                id = it,
                goBack = { navigator.goBack() },
                updateToolbarState = updateToolbarState
            )
        }
    }
}
