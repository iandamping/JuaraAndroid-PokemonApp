package com.example.juaraandroid_pokemonapp.core.data.datasource

import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.ApiInterface
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.NetworkConstant.EMPTY_DATA
import com.example.juaraandroid_pokemonapp.core.data.datasource.response.PokemonDetailResponse
import com.example.juaraandroid_pokemonapp.core.data.datasource.response.PokemonResultsResponse
import com.example.juaraandroid_pokemonapp.core.data.datasource.response.PokemonSpeciesDetailResponse
import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult
import com.example.juaraandroid_pokemonapp.core.domain.common.DataSourceResult
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PokemonRemoteDataSourceImpl @Inject constructor(
    baseSource: BaseSource,
    private val api: ApiInterface
) : PokemonRemoteDataSource, BaseSource by baseSource {
    override suspend fun getPokemon(): DataSourceResult<List<PokemonResultsResponse>> {
        return when (val response = oneShotCalls { api.getMainPokemon() }) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> if (response.data.pokemonResults.isNullOrEmpty()) {
                DataSourceResult.SourceError(Exception(EMPTY_DATA))
            } else DataSourceResult.SourceValue(response.data.pokemonResults)
        }
    }

    override suspend fun getPaginationPokemon(offset: Int): DataSourceResult<List<PokemonResultsResponse>> {
        return when (val response = oneShotCalls { api.getPaginationMainPokemon(offset) }) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> if (response.data.pokemonResults.isNullOrEmpty()) {
                DataSourceResult.SourceError(Exception(EMPTY_DATA))
            } else DataSourceResult.SourceValue(response.data.pokemonResults)
        }
    }

    override suspend fun getDetailPokemon(url: String): PokemonDetailResponse {
        return api.getPokemon(url)
    }

    override suspend fun getDetailPokemonCharacteristic(id: Int): DataSourceResult<String> {
        return when (val response = oneShotCalls { api.getPokemonCharacteristic(id) }) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> DataSourceResult.SourceValue(response.data.descriptions[7].description)
        }
    }

    override suspend fun getPokemonLocationAreas(id: Int): DataSourceResult<List<String>> {
        return when (val response = oneShotCalls { api.getPokemonLocationAreas(id) }) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> DataSourceResult.SourceValue(response.data.map { it.area.name })
        }
    }

    override suspend fun getPokemonById(id: Int): DataSourceResult<PokemonDetailResponse> {
        return when (val response = oneShotCalls { api.getPokemonById(id) }) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> DataSourceResult.SourceValue(response.data)
        }
    }

    override suspend fun getDetailSpeciesPokemon(url: String): PokemonSpeciesDetailResponse {
        return api.getPokemonSpecies(url)
    }
}
