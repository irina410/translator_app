package com.example.translatorapp.data.remote

import com.example.translatorapp.domain.model.Word


fun WordDto.toDomainWord(): Word {
    return Word(
        id = text.lowercase(),
        english = text,
        russian = meanings.firstOrNull()?.translation?.text,
        isFavorite = false,
        translatedAt = System.currentTimeMillis()
    )
}
