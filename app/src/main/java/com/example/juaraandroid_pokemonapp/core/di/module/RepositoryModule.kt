package com.example.juaraandroid_pokemonapp.core.di.module

import com.example.juaraandroid_pokemonapp.core.data.repository.PokemonRepositoryImpl
import com.example.juaraandroid_pokemonapp.core.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsPokemonRepository(repository: PokemonRepositoryImpl): PokemonRepository
}