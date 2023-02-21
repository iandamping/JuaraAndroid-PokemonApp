package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.eggGroup

import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.ApiInterface
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.base.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.response.ItemPokemonEggResponse
import javax.inject.Inject

class RemotePokemonEggGroupManagerImpl @Inject constructor(
    baseSource: BaseSource,
    private val api: ApiInterface
) : RemotePokemonEggGroupManager, BaseSource by baseSource {

    override suspend fun getPokemonEggGroup(url: String): List<ItemPokemonEggResponse> {
        return try {
            api.getPokemonEggGroup(url).eggGroupSpecies
        } catch (e: Exception) {
            throw e
        }
    }
}