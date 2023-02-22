package com.example.juaraandroid_pokemonapp.core.di.module

import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.pagination.CachePokemonPaginationManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.pagination.CachePokemonPaginationManagerImpl
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.quiz.CachePokemonQuizManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.quiz.CachePokemonQuizManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface CacheDataManagerModule {

    @Binds
    fun bindsCachePokemonPaginationManager(impl: CachePokemonPaginationManagerImpl): CachePokemonPaginationManager

    @Binds
    fun bindsCachePokemonQuizManager(impl: CachePokemonQuizManagerImpl): CachePokemonQuizManager
}