package com.example.translatorapp.ui.translator_screen

import com.example.translatorapp.domain.model.Word


data class TranslatorState(
    val inputWord: String = "",
    val translation: String = "",
    val history: List<Word> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isFavorite: Boolean = false
)
