package com.example.translatorapp.domain.repository
import com.example.translatorapp.domain.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {

    suspend fun translate(word: String): Word
    fun getHistory(): Flow<List<Word>>
    fun getFavorites(): Flow<List<Word>>
    suspend fun toggleFavorite(wordId: String)
    suspend fun deleteWord(wordId: String)
}