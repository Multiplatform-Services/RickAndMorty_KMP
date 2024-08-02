package com.rickandmorty.kmp.presentation.toolbar

import androidx.compose.ui.graphics.vector.ImageVector

data class ToolbarState(
    val title: String,
    val subtitle: String? = null,
    val actions: List<ToolbarAction> = emptyList(),
    val showBackButton: Boolean = false,
    val onBack: (() -> Unit)? = null
)

data class ToolbarAction(
    val icon: ImageVector,
    val onClick: () -> Unit
)
