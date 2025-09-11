package com.example.translatorapp.data.remote
import retrofit2.http.GET
import retrofit2.http.Query


interface SkyengApi {
    @GET("/api/public/v1/words/search")
    suspend fun searchWord(
        @Query("search") query: String
    ): List<WordDto>
}