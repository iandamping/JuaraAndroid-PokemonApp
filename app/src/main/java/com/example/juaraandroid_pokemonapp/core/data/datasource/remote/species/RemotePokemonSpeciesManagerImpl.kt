package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.species

import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.ApiInterface
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.base.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.response.PokemonSpeciesDetailResponse
import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult
import javax.inject.Inject

class RemotePokemonSpeciesManagerImpl @Inject constructor(
    baseSource: BaseSource,
    private val api: ApiInterface,
) :
    RemotePokemonSpeciesManager, BaseSource by baseSource {

    override suspend fun getDetailSpeciesPokemon(id: Int): ApiResult<PokemonSpeciesDetailResponse> {
        return oneShotCalls(api.getPokemonSpecies(id))
    }
}