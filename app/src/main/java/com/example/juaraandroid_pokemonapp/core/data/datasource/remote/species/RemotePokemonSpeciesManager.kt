package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.species

import com.example.juaraandroid_pokemonapp.core.data.datasource.response.PokemonSpeciesDetailResponse
import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult

interface RemotePokemonSpeciesManager {

    suspend fun getDetailSpeciesPokemon(id: Int): ApiResult<PokemonSpeciesDetailResponse>
}