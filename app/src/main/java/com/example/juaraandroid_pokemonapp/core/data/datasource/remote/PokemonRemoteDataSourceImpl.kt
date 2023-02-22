package com.example.juaraandroid_pokemonapp.core.data.datasource.remote

import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.area.RemotePokemonAreaManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.characteristic.RemotePokemonCharacteristicManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.eggGroup.RemotePokemonEggGroupManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.evolution.RemotePokemonEvolutionManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.pokemon.RemotePokemonManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.species.RemotePokemonSpeciesManager
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
    private val speciesManager: RemotePokemonSpeciesManager,
    private val areaManager: RemotePokemonAreaManager,
    private val characteristicManager: RemotePokemonCharacteristicManager,
    private val eggGroupManager: RemotePokemonEggGroupManager,
    private val pokemonManager: RemotePokemonManager,
    private val evolutionManager: RemotePokemonEvolutionManager
) : PokemonRemoteDataSource {
    override suspend fun getPokemon(): DataSourceResult<List<PokemonResultsResponse>> {
        return when (val response = pokemonManager.getPokemon()) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> DataSourceResult.SourceValue(response.data)
        }
    }

    override suspend fun getPaginationPokemon(offset: Int): DataSourceResult<List<PokemonResultsResponse>> {
        return when (val response = pokemonManager.getPaginationPokemon(offset)) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> DataSourceResult.SourceValue(response.data)
        }
    }

    @kotlin.jvm.Throws(Exception::class)
    override suspend fun getDetailPokemon(url: String): PokemonDetailResponse {
        return try {
            pokemonManager.getDetailPokemon(url)
        } catch (e: Exception) {
            throw e
        }
    }

    @kotlin.jvm.Throws(Exception::class)
    override suspend fun getDetailPokemonDirectByName(name: String): PokemonDetailResponse {
        return try {
            pokemonManager.getDetailPokemonDirectByName(name)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPokemonEggGroup(url: String): List<ItemPokemonEggResponse> {
        return try {
            eggGroupManager.getPokemonEggGroup(url)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPokemonEvolution(url: String): EvolvingPokemon {
        return try {
            evolutionManager.getPokemonEvolution(url)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getDetailPokemonCharacteristic(id: Int): DataSourceResult<String> {
        return when (val response = characteristicManager.getDetailPokemonCharacteristic(id)) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> DataSourceResult.SourceValue(response.data)
        }
    }

    override suspend fun getPokemonLocationAreas(id: Int): DataSourceResult<List<String>> {
        return when (val response = areaManager.getPokemonLocationAreas(id)) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> DataSourceResult.SourceValue(response.data)
        }
    }

    override suspend fun getPokemonById(id: Int): DataSourceResult<PokemonDetailResponse> {
        return when (val response = pokemonManager.getPokemonById(id)) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> DataSourceResult.SourceValue(response.data)
        }
    }

    override suspend fun getPokemonByName(name: String): DataSourceResult<PokemonDetailResponse> {
        return when (val response = pokemonManager.getPokemonByName(name)) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> DataSourceResult.SourceValue(response.data)
        }
    }

    override suspend fun getDetailSpeciesPokemon(id: Int): DataSourceResult<PokemonSpeciesDetailResponse> {
        return when (val response = speciesManager.getDetailSpeciesPokemon(id)) {
            is ApiResult.Error -> DataSourceResult.SourceError(response.exception)
            is ApiResult.Success -> DataSourceResult.SourceValue(response.data)
        }
    }
}
