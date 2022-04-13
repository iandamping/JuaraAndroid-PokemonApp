package com.example.juaraandroid_pokemonapp.core.domain.usecase

import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.PokemonFavoriteEntity
import com.example.juaraandroid_pokemonapp.core.domain.model.UiState
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetail
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetailSpecies
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 10,June,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PokemonUseCase {

    fun getPokemon(): Flow<UiState<List<PokemonDetail>>>

    fun getDetailSpeciesPokemon(url: String): Flow<PokemonDetailSpecies>

    fun getDetailPokemonCharacteristic(id: Int): Flow<UiState<String>>

    fun getPokemonLocationAreas(id: Int): Flow<UiState<List<String>>>

    fun getPokemonById(id: Int): Flow<UiState<PokemonDetail>>

    suspend fun saveFavorite(data: PokemonDetail)

    suspend fun clearFavorite(id: Int)

    fun getListFavorite(): Flow<List<PokemonFavoriteEntity>>
}