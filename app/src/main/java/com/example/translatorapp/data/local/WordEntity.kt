package com.example.translatorapp.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey val id: String,
    val english: String,
    val russian: String?,
    val isFavorite: Boolean = false,
    val translatedAt: Long = System.currentTimeMillis()
)