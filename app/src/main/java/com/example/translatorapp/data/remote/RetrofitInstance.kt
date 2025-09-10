package com.example.translatorapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://dictionary.skyeng.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SkyengApi by lazy {
        retrofit.create(SkyengApi::class.java)
    }
}