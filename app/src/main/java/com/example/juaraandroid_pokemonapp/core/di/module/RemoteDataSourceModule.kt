package com.example.juaraandroid_pokemonapp.core.di.module

import com.example.juaraandroid_pokemonapp.core.data.datasource.PokemonRemoteDataSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.PokemonRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataSourceModule {

    @Binds
    @Singleton
    fun bindsPokemonRemoteSource(dataSource: PokemonRemoteDataSourceImpl): PokemonRemoteDataSource
}