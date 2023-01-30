package com.example.juaraandroid_pokemonapp.core.di.module

import android.content.Context
import androidx.room.Room
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.PokemonDatabase
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.dao.PokemonQuizDao
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.dao.PokemonRemoteKeyDao
import com.spesolution.myapplication.core.data.datasource.cache.room.PokemonPaginationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): PokemonDatabase {
        return Room
            .databaseBuilder(context, PokemonDatabase::class.java, "pokemon.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePokemonRemoteKeyDao(db: PokemonDatabase): PokemonRemoteKeyDao {
        return db.remoteKeyDao()
    }

    @Provides
    fun providePokemonPaginationDao(db: PokemonDatabase): PokemonPaginationDao {
        return db.paginationDao()
    }

    @Provides
    fun providePokemonQuizDao(db: PokemonDatabase): PokemonQuizDao {
        return db.quizDao()
    }


}