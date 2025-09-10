package com.example.translatorapp.domain.model

data class Word(
    val id: String,
    val english: String,
    val russian: String?,
    val isFavorite: Boolean = false,
    val translatedAt: Long = System.currentTimeMillis()
)
