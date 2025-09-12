package com.example.translatorapp.data.repository

import com.example.translatorapp.data.local.WordDao
import com.example.translatorapp.data.remote.SkyengApi
import com.example.translatorapp.data.remote.toDomain
import com.example.translatorapp.data.remote.toDomainWord
import com.example.translatorapp.data.remote.toEntity
import com.example.translatorapp.domain.model.Word
import com.example.translatorapp.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val api: SkyengApi,
    private val dao: WordDao,
) : WordRepository {

    override suspend fun translate(word: String): Word {
        val response = api.searchWord(word)
        val dto = response.firstOrNull() ?: throw Exception("Перевод не найден")
        val domainWord = dto.toDomainWord()

        val existing = dao.getWordById(domainWord.id)
        if (existing != null) {
            // Сохраняем перевод, но сохраняем старое isFavorite
            val updated = domainWord.toEntity().copy(isFavorite = existing.isFavorite)
            dao.insertWord(updated)
        } else {
            dao.insertWord(domainWord.toEntity())
        }

        return domainWord
    }


    override fun getHistory(): Flow<List<Word>> {
        return dao.getAllWords().map { list -> list.map { it.toDomain() } }
    }

    override fun getFavorites(): Flow<List<Word>> {
        return dao.getFavoriteWords()
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun toggleFavorite(wordId: String) {
        val existing = dao.getWordById(wordId) ?: return
        val updated = existing.copy(isFavorite = !existing.isFavorite)
        dao.updateWord(updated)
    }


    override suspend fun deleteWord(wordId: String) {
        dao.deleteWordById(wordId)
    }
}
