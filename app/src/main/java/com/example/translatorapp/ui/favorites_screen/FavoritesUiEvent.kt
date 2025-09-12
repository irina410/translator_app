package com.example.translatorapp.ui.favorites_screen

sealed class FavoritesUiEvent {
    data class ShowMessage(val text: String) : FavoritesUiEvent()
}