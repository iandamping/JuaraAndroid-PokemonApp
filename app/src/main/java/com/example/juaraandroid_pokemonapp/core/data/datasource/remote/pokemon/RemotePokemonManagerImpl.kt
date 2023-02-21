package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.pokemon

import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.ApiInterface
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.base.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant
import com.example.juaraandroid_pokemonapp.core.data.datasource.response.PokemonDetailResponse
import com.example.juaraandroid_pokemonapp.core.data.datasource.response.PokemonResultsResponse
import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult
import javax.inject.Inject

class RemotePokemonManagerImpl @Inject constructor(
    baseSource: BaseSource,
    private val api: ApiInterface
) : RemotePokemonManager, BaseSource by baseSource {
    override suspend fun getPokemon(): ApiResult<List<PokemonResultsResponse>> {
        return when (val response = oneShotCalls(api.getMainPokemon())) {
            is ApiResult.Error -> ApiResult.Error(response.exception)
            is ApiResult.Success -> if (response.data.pokemonResults.isEmpty()) {
                ApiResult.Error(Exception(NetworkConstant.EMPTY_DATA))
            } else ApiResult.Success(response.data.pokemonResults)
        }
    }

    override suspend fun getPaginationPokemon(offset: Int): ApiResult<List<PokemonResultsResponse>> {
        return when (val response = oneShotCalls(api.getPaginationMainPokemon(offset))) {
            is ApiResult.Error -> ApiResult.Error(response.exception)
            is ApiResult.Success -> if (response.data.pokemonResults.isEmpty()) {
                ApiResult.Error(Exception(NetworkConstant.EMPTY_DATA))
            } else ApiResult.Success(response.data.pokemonResults)
        }
    }

    override suspend fun getDetailPokemon(url: String): PokemonDetailResponse {
        return try {
            api.getPokemon(url)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getDetailPokemonDirectByName(name: String): PokemonDetailResponse {
        return try {
            api.getPokemonDirectByName(name)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPokemonById(id: Int): ApiResult<PokemonDetailResponse> {
        return when (val response = oneShotCalls(api.getPokemonById(id))) {
            is ApiResult.Error -> ApiResult.Error(response.exception)
            is ApiResult.Success -> ApiResult.Success(response.data)
        }
    }

    override suspend fun getPokemonByName(name: String): ApiResult<PokemonDetailResponse> {
        return when (val response = oneShotCalls(api.getPokemonByName(name))) {
            is ApiResult.Error -> ApiResult.Error(response.exception)
            is ApiResult.Success -> ApiResult.Success(response.data)
        }
    }
}