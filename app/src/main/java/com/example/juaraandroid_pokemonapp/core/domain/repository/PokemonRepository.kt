package com.example.juaraandroid_pokemonapp.core.domain.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonFavoriteEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonPaginationEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonQuizEntity
import com.example.juaraandroid_pokemonapp.core.domain.common.DomainResult
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetailSpecies
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 07,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PokemonRepository {

    suspend fun getPokemon(): DomainResult<List<PokemonDetail>>

    fun getPaginationPokemonRemoteMediator(): RemoteMediator<Int, PokemonPaginationEntity>

    fun getPaginationPokemonPagingSource(): PagingSource<Int, PokemonPaginationEntity>

    suspend fun getDetailSpeciesPokemon(url: String): DomainResult<PokemonDetailSpecies>

    suspend fun getDetailPokemonCharacteristic(id: Int): DomainResult<String>

    suspend fun getPokemonLocationAreas(id: Int): DomainResult<List<String>>

    suspend fun getPokemonById(id: Int): DomainResult<PokemonDetail>

    suspend fun saveFavorite(data: PokemonDetail)

    suspend fun clearFavorite(id: Int)

    fun getListFavorite(): Flow<List<PokemonFavoriteEntity>>

    fun getListOfQuiz():Flow<List<PokemonDetail>>
}