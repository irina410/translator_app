package com.example.translatorapp.data.remote

import com.example.translatorapp.data.local.WordEntity
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
fun Word.toEntity(): WordEntity {
    return WordEntity(
        id = id,
        english = english,
        russian = russian,
        isFavorite = isFavorite,
        translatedAt = translatedAt
    )
}
fun WordEntity.toDomain(): Word {
    return Word(
        id = id,
        english = english,
        russian = russian,
        isFavorite = isFavorite,
        translatedAt = translatedAt
    )
}