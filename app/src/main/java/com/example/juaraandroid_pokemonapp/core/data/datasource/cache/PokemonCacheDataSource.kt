package com.example.juaraandroid_pokemonapp.core.data.datasource.cache

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonFavoriteEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonPaginationEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonRemoteKeysEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.response.PokemonResultsResponse
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonCacheDataSource {

    suspend fun <T> databaseTransaction(block: suspend () -> T): T

    suspend fun clearFavorite(id: Int)

    suspend fun clearPagination()

    suspend fun clearRemoteKeys()

    suspend fun saveFavorite(data: PokemonDetail)

    suspend fun savePagination(data: List<PokemonDetail>)

    suspend fun saveRemoteKeys(data: List<PokemonRemoteKeysEntity>)

    fun getListFavorite(): Flow<List<PokemonFavoriteEntity>>

    fun getPagination(): PagingSource<Int, PokemonPaginationEntity>

    suspend fun getRemoteKeys(data: Int):PokemonRemoteKeysEntity?

    suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity?

    suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity?

    suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity?


}