package com.example.translatorapp.ui.translator_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translatorapp.domain.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranslatorViewModel @Inject constructor(
    private val repository: WordRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TranslatorState())
    val state: StateFlow<TranslatorState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getHistory()
                .collect { history ->
                    _state.update { it.copy(history = history) }
                }
        }
    }

    fun onEvent(event: TranslatorEvent) {
        when(event) {
            is TranslatorEvent.EnteredWord -> {
                _state.update { it.copy(inputWord = event.word) }
            }
            is TranslatorEvent.TranslateClicked -> {
                translateWord()
            }
            is TranslatorEvent.ToggleFavorite -> {
                toggleFavorite(event.wordId)
            }
        }
    }

    private fun translateWord() {
        val word = _state.value.inputWord.trim()
        if (word.isEmpty()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val translated = repository.translate(word)
                _state.update {
                    it.copy(
                        translation = translated.russian.toString(),
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    private fun toggleFavorite(wordId: String) {
        viewModelScope.launch {
            repository.toggleFavorite(wordId)

            val updatedHistory = _state.value.history.map { word ->
                if (word.id == wordId) word.copy(isFavorite = !word.isFavorite) else word
            }
            _state.update { it.copy(history = updatedHistory) }
        }
    }
}
