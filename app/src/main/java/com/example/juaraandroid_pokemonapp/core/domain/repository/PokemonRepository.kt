package com.example.juaraandroid_pokemonapp.core.domain.repository

import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.PokemonFavoriteEntity
import com.example.juaraandroid_pokemonapp.core.domain.model.DomainResult
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetail
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetailSpecies
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 07,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PokemonRepository {

    fun getPokemon(): Flow<DomainResult<List<PokemonDetail>>>

    fun getDetailSpeciesPokemon(url: String): Flow<PokemonDetailSpecies>

    fun getDetailPokemonCharacteristic(id: Int): Flow<DomainResult<String>>

    fun getPokemonLocationAreas(id: Int): Flow<DomainResult<List<String>>>

    fun getPokemonById(id: Int): Flow<DomainResult<PokemonDetail>>

    suspend fun saveFavorite(data: PokemonDetail)

    suspend fun clearFavorite(id: Int)

    fun getListFavorite(): Flow<List<PokemonFavoriteEntity>>

}