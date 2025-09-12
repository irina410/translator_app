package com.example.translatorapp.di

import android.content.Context
import androidx.room.Room
import com.example.translatorapp.data.local.AppDatabase
import com.example.translatorapp.data.local.WordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "translator_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideWordDao(db: AppDatabase): WordDao = db.wordDao()
}
