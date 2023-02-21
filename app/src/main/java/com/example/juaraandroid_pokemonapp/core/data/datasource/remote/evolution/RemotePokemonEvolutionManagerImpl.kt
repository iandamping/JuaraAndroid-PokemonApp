package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.evolution

import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.ApiInterface
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.base.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.response.EvolvingPokemon
import javax.inject.Inject

class RemotePokemonEvolutionManagerImpl @Inject constructor(
    baseSource: BaseSource,
    private val api: ApiInterface
) : RemotePokemonEvolutionManager, BaseSource by baseSource {
    override suspend fun getPokemonEvolution(url: String): EvolvingPokemon {
        return try {
            api.getPokemonEvolution(url).evolutionChain.evolveTo.first().evolvingPokemonSpecies
        } catch (e: Exception) {
            throw e
        }
    }
}