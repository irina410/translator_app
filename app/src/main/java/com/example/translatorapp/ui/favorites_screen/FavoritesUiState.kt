package com.example.translatorapp.ui.favorites_screen

import com.example.translatorapp.domain.model.Word

data class FavoritesUiState(
    val favorites: List<Word> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)