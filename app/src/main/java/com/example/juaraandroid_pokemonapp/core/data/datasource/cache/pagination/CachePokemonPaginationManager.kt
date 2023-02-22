package com.example.juaraandroid_pokemonapp.core.data.datasource.cache.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonPaginationEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonRemoteKeysEntity
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail

interface CachePokemonPaginationManager {

    suspend fun savePagination(data: List<PokemonDetail>)

    suspend fun saveRemoteKeys(data: List<PokemonRemoteKeysEntity>)

    fun getPagination(): PagingSource<Int, PokemonPaginationEntity>

    suspend fun getRemoteKeys(data: Int): PokemonRemoteKeysEntity?

    suspend fun getRemoteKeysRepoId(id: Int): PokemonRemoteKeysEntity?

    suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity?

    suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity?

    suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity?

    suspend fun clearPagination()

    suspend fun clearRemoteKeys()


}