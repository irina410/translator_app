package com.example.translatorapp.ui.translator_screen

sealed class TranslatorEvent {
    data class EnteredWord(val word: String) : TranslatorEvent()
    object TranslateClicked : TranslatorEvent()
    data class ToggleFavorite(val wordId: String) : TranslatorEvent()
    data class DeleteWord(val wordId: String) : TranslatorEvent()
}