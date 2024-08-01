package com.rickandmorty.kmp.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.rickandmorty.kmp.data.entity.CharacterEntity
import kmp_rickandmorty.composeapp.generated.resources.Res
import kmp_rickandmorty.composeapp.generated.resources.characters
import kmp_rickandmorty.composeapp.generated.resources.details
import kmp_rickandmorty.composeapp.generated.resources.home
import org.jetbrains.compose.resources.StringResource

sealed class Screen(
    val route: String,
    val stringRes: StringResource,
    val icon: ImageVector,
    val subScreens: List<SubScreen> = emptyList()
) {

    data object Home : Screen(
        route = "home",
        stringRes = Res.string.home,
        icon = Icons.Filled.Home,
        subScreens = listOf(
            SubScreen.CharacterList("home/characterList"),
            SubScreen.CharacterDetail("home/characterDetail/{id}")
        )
    ) {
        val characterList get() = subScreens.filterIsInstance<SubScreen.CharacterList>().first()
        val characterDetail get() = subScreens.filterIsInstance<SubScreen.CharacterDetail>().first()
        fun characterDetailRoute(id: Int): String {
            return "home/characterDetail/$id"
        }
    }

    companion object {
        val defaultModifier: @Composable () -> Modifier =
            { Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background) }
    }
}

sealed class SubScreen(val route: String, val stringRes: StringResource) {
    class CharacterList(route: String) : SubScreen(route, Res.string.characters)
    class CharacterDetail(route: String) : SubScreen(route, Res.string.details)
}
