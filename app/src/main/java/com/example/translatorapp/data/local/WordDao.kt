package com.example.translatorapp.data.local
import androidx.room.*

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: WordEntity)

    @Delete
    suspend fun deleteWord(word: WordEntity)

    @Query("SELECT * FROM words ORDER BY translatedAt DESC")
    suspend fun getAllWords(): List<WordEntity>

    @Query("SELECT * FROM words WHERE isFavorite = 1")
    suspend fun getFavoriteWords(): List<WordEntity>

    @Query("UPDATE words SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)
}
