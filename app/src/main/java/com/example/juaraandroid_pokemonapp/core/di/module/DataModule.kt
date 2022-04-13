package com.example.juaraandroid_pokemonapp.core.di.module

import com.example.juaraandroid_pokemonapp.core.data.datasource.PokemonRemoteDataSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.PokemonRemoteDataSourceImpl
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.PokemonCacheDataSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.PokemonCacheDataSourceImpl
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.BaseSourceImpl
import com.example.juaraandroid_pokemonapp.core.data.repository.PokemonRepositoryImpl
import com.example.juaraandroid_pokemonapp.core.domain.repository.PokemonRepository
import com.example.juaraandroid_pokemonapp.core.domain.usecase.PokemonUseCase
import com.example.juaraandroid_pokemonapp.core.domain.usecase.PokemonUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ian Damping on 06,September,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindsBaseSource(baseSource: BaseSourceImpl): BaseSource

    @Binds
    @Singleton
    fun bindsPokemonRemoteSource(dataSource: PokemonRemoteDataSourceImpl): PokemonRemoteDataSource

    @Binds
    @Singleton
    fun bindsPokemonCacheSource(dataSource: PokemonCacheDataSourceImpl): PokemonCacheDataSource

    @Binds
    @Singleton
    fun bindsPokemonRepository(repository: PokemonRepositoryImpl): PokemonRepository

    @Binds
    @Singleton
    fun bindsPokemonUseCase(useCase: PokemonUseCaseImpl): PokemonUseCase
}