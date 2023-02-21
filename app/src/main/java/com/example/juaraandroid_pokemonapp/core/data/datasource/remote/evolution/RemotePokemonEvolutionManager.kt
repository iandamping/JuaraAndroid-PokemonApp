package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.evolution

import com.example.juaraandroid_pokemonapp.core.data.datasource.response.EvolvingPokemon

interface RemotePokemonEvolutionManager {

    @kotlin.jvm.Throws(Exception::class)
    suspend fun getPokemonEvolution(url: String): EvolvingPokemon
}