package com.example.translatorapp.data.repository

import com.example.translatorapp.data.local.WordDao
import com.example.translatorapp.data.remote.SkyengApi
import com.example.translatorapp.domain.model.Word
import com.example.translatorapp.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val api: SkyengApi,
    private val dao: WordDao
) : WordRepository {

    override suspend fun translate(word: String): Word {
        TODO("Not yet implemented")
    }

    override fun getHistory(): Flow<List<Word>> {
        TODO("Not yet implemented")
    }

    override fun getFavorites(): Flow<List<Word>> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleFavorite(wordId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteWord(wordId: String) {
        TODO("Not yet implemented")
    }
}
