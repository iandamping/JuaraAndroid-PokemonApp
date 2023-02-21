package com.example.juaraandroid_pokemonapp.core.di.module

import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.PokemonRemoteDataSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.PokemonRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataSourceModule {

    @Binds
    fun bindsPokemonRemoteSource(dataSource: PokemonRemoteDataSourceImpl): PokemonRemoteDataSource
}