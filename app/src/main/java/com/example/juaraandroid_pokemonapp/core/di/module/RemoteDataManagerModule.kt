package com.example.juaraandroid_pokemonapp.core.di.module

import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.area.RemotePokemonAreaManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.area.RemotePokemonAreaManagerImpl
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.characteristic.RemotePokemonCharacteristicManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.characteristic.RemotePokemonCharacteristicManagerImpl
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.eggGroup.RemotePokemonEggGroupManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.eggGroup.RemotePokemonEggGroupManagerImpl
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.evolution.RemotePokemonEvolutionManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.evolution.RemotePokemonEvolutionManagerImpl
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.pokemon.RemotePokemonManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.pokemon.RemotePokemonManagerImpl
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.species.RemotePokemonSpeciesManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.species.RemotePokemonSpeciesManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataManagerModule {

    @Binds
    fun bindsRemotePokemonAreaManager(impl: RemotePokemonAreaManagerImpl): RemotePokemonAreaManager

    @Binds
    fun bindsRemotePokemonCharacteristicManager(impl: RemotePokemonCharacteristicManagerImpl): RemotePokemonCharacteristicManager

    @Binds
    fun bindsRemotePokemonEggGroupManager(impl: RemotePokemonEggGroupManagerImpl): RemotePokemonEggGroupManager

    @Binds
    fun bindsRemotePokemonEvolutionManager(impl: RemotePokemonEvolutionManagerImpl): RemotePokemonEvolutionManager

    @Binds
    fun bindsRemotePokemonManager(impl: RemotePokemonManagerImpl): RemotePokemonManager

    @Binds
    fun bindsRemotePokemonSpeciesManager(impl: RemotePokemonSpeciesManagerImpl): RemotePokemonSpeciesManager
}