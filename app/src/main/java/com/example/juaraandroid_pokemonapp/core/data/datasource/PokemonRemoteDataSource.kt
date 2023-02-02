package com.example.juaraandroid_pokemonapp.core.data.datasource

import com.example.juaraandroid_pokemonapp.core.data.datasource.response.*
import com.example.juaraandroid_pokemonapp.core.domain.common.DataSourceResult

/**
 * Created by Ian Damping on 07,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PokemonRemoteDataSource {

    suspend fun getPokemon(): DataSourceResult<List<PokemonResultsResponse>>

    suspend fun getPaginationPokemon(offset: Int): DataSourceResult<List<PokemonResultsResponse>>

    @kotlin.jvm.Throws(Exception::class)
    suspend fun getDetailPokemon(url: String): PokemonDetailResponse

    @kotlin.jvm.Throws(Exception::class)
    suspend fun getDetailPokemonDirectByName(name: String): PokemonDetailResponse

    @kotlin.jvm.Throws(Exception::class)
    suspend fun getPokemonEggGroup(url: String): List<ItemPokemonEggResponse>

    @kotlin.jvm.Throws(Exception::class)
    suspend fun getPokemonEvolution(url: String): EvolvingPokemon

    suspend fun getDetailPokemonCharacteristic(id: Int): DataSourceResult<String>

    suspend fun getPokemonLocationAreas(id: Int): DataSourceResult<List<String>>

    suspend fun getPokemonById(id: Int): DataSourceResult<PokemonDetailResponse>

    suspend fun getPokemonByName(name: String): DataSourceResult<PokemonDetailResponse>

    suspend fun getDetailSpeciesPokemon(id: Int): DataSourceResult<PokemonSpeciesDetailResponse>
}