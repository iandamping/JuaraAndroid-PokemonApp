package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.pokemon

import com.example.juaraandroid_pokemonapp.core.data.datasource.response.PokemonDetailResponse
import com.example.juaraandroid_pokemonapp.core.data.datasource.response.PokemonResultsResponse
import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult

interface RemotePokemonManager {

    suspend fun getPokemon(): ApiResult<List<PokemonResultsResponse>>

    suspend fun getPaginationPokemon(offset: Int): ApiResult<List<PokemonResultsResponse>>

    @kotlin.jvm.Throws(Exception::class)
    suspend fun getDetailPokemon(url: String): PokemonDetailResponse

    @kotlin.jvm.Throws(Exception::class)
    suspend fun getDetailPokemonDirectByName(name: String): PokemonDetailResponse

    suspend fun getPokemonById(id: Int): ApiResult<PokemonDetailResponse>

    suspend fun getPokemonByName(name: String): ApiResult<PokemonDetailResponse>
}