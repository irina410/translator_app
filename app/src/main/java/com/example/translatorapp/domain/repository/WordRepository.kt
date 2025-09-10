package com.example.translatorapp.domain.repository

import com.example.translatorapp.domain.model.Word

interface WordRepository {
    suspend fun getWordById(id: String): Word?
    suspend fun getWordByEnglish(english: String): Word?
    suspend fun saveWord(word: Word)
    suspend fun deleteWord(id: String)
    suspend fun getAllWords(): List<Word>
}
