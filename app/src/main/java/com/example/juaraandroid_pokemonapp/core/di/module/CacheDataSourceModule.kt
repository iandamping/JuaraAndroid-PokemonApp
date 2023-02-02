package com.example.juaraandroid_pokemonapp.core.di.module

import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.PokemonCacheDataSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.PokemonCacheDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CacheDataSourceModule {

    @Binds
    @Singleton
    fun bindsPokemonCacheSource(dataSource: PokemonCacheDataSourceImpl): PokemonCacheDataSource
}