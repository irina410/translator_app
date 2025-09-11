package com.example.translatorapp.data.remote

data class WordDto (
    val id: Int,
    val text: String,
    val meanings: List<MeaningDto>
)