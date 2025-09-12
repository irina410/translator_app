package com.example.translatorapp.ui.favorites_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translatorapp.domain.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: WordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState(isLoading = true))
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    private val _eventFlow = Channel<FavoritesUiEvent>(Channel.BUFFERED)
    val eventFlow = _eventFlow.receiveAsFlow() // для подписки в Composable

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            repository.getFavorites()
                .collect { list ->
                    _uiState.value = FavoritesUiState(
                        favorites = list,
                        isLoading = false
                    )
                }
        }
    }

    fun toggleFavorite(wordId: String) {
        viewModelScope.launch {
            repository.toggleFavorite(wordId)
            _eventFlow.send(FavoritesUiEvent.ShowMessage("Состояние избранного изменено"))
            loadFavorites() // обновляем список после изменения
        }
    }

    fun deleteWord(wordId: String) {
        viewModelScope.launch {
            repository.deleteWord(wordId)
            _eventFlow.send(FavoritesUiEvent.ShowMessage("Слово удалено"))
            loadFavorites() // обновляем список после удаления
        }
    }

}
