package com.example.juaraandroid_pokemonapp.core.data.datasource

import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.ApiInterface
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.NetworkConstant.EMPTY_DATA
import com.example.juaraandroid_pokemonapp.core.data.datasource.response.*
import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult
import com.example.juaraandroid_pokemonapp.core.domain.common.DataSourceResult
import java.util.*
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
        return when (val response = oneShotCalls(api.getMainPokemon())) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> if (response.data.pokemonResults.isEmpty()) {
                DataSourceResult.SourceError(Exception(EMPTY_DATA))
            } else DataSourceResult.SourceValue(response.data.pokemonResults)
        }
    }

    override suspend fun getPaginationPokemon(offset: Int): DataSourceResult<List<PokemonResultsResponse>> {
        return when (val response = oneShotCalls(api.getPaginationMainPokemon(offset))) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> if (response.data.pokemonResults.isEmpty()) {
                DataSourceResult.SourceError(Exception(EMPTY_DATA))
            } else DataSourceResult.SourceValue(response.data.pokemonResults)
        }
    }

    @kotlin.jvm.Throws(Exception::class)
    override suspend fun getDetailPokemon(url: String): PokemonDetailResponse {
        return try {
            api.getPokemon(url)
        } catch (e: Exception) {
            throw e
        }
    }

    @kotlin.jvm.Throws(Exception::class)
    override suspend fun getDetailPokemonDirectByName(name: String): PokemonDetailResponse {
        return try {
            api.getPokemonDirectByName(name)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPokemonEggGroup(url: String): List<ItemPokemonEggResponse> {
        return try {
            api.getPokemonEggGroup(url).eggGroupSpecies
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPokemonEvolution(url: String): EvolvingPokemon {
        return try {
            api.getPokemonEvolution(url).evolutionChain.evolveTo.first().evolvingPokemonSpecies
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getDetailPokemonCharacteristic(id: Int): DataSourceResult<String> {
        return when (val response = oneShotCalls(api.getPokemonCharacteristic(id))) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> {
                val position = response.data.descriptions.indexOfFirst { it.language.name == "en" }
                DataSourceResult.SourceValue(response.data.descriptions[position].description)
            }
        }
    }

    override suspend fun getPokemonLocationAreas(id: Int): DataSourceResult<List<String>> {
        return when (val response = oneShotCalls(api.getPokemonLocationAreas(id))) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> {
                if (response.data.isNotEmpty()) {
                    DataSourceResult.SourceValue(response.data.map { it.area.name })
                } else DataSourceResult.SourceError(Exception(EMPTY_DATA))
            }
        }
    }

    override suspend fun getPokemonById(id: Int): DataSourceResult<PokemonDetailResponse> {
        return when (val response = oneShotCalls(api.getPokemonById(id))) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> DataSourceResult.SourceValue(response.data)
        }
    }

    override suspend fun getPokemonByName(name: String): DataSourceResult<PokemonDetailResponse> {
        return when (val response = oneShotCalls(api.getPokemonByName(name))) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> DataSourceResult.SourceValue(response.data)
        }
    }

    override suspend fun getDetailSpeciesPokemon(id: Int): DataSourceResult<PokemonSpeciesDetailResponse> {
        return when (val response = oneShotCalls(api.getPokemonSpecies(id))) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> DataSourceResult.SourceValue(response.data)
        }
    }
}
