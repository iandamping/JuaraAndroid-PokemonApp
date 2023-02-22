package com.example.juaraandroid_pokemonapp.core.data.datasource.cache.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.dao.PokemonRemoteKeyDao
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonPaginationEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonRemoteKeysEntity
import com.example.juaraandroid_pokemonapp.core.domain.common.mapToPaginationDatabase
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import com.spesolution.myapplication.core.data.datasource.cache.room.PokemonPaginationDao
import javax.inject.Inject

class CachePokemonPaginationManagerImpl @Inject constructor(
    private val paginationDao: PokemonPaginationDao,
    private val remoteKeysDao: PokemonRemoteKeyDao
) : CachePokemonPaginationManager {

    override suspend fun savePagination(data: List<PokemonDetail>) {
        paginationDao.insertPokemon(*data.map { it.mapToPaginationDatabase() }.toTypedArray())
    }

    override suspend fun saveRemoteKeys(data: List<PokemonRemoteKeysEntity>) {
        remoteKeysDao.insertKey(*data.toTypedArray())
    }


    override fun getPagination(): PagingSource<Int, PokemonPaginationEntity> {
        return paginationDao.loadPokemon()
    }

    override suspend fun getRemoteKeys(data: Int): PokemonRemoteKeysEntity? {
        return remoteKeysDao.remoteKeysRepoId(data)
    }

    override suspend fun getRemoteKeysRepoId(id: Int): PokemonRemoteKeysEntity? {
        return remoteKeysDao.remoteKeysRepoId(id)
    }

    override suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { poke ->
            getRemoteKeysRepoId(poke.pokemonId)
        }
    }

    override suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { poke ->
                getRemoteKeysRepoId(poke.pokemonId)
            }
    }

    override suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.pokemonId?.let { repoId ->
                getRemoteKeysRepoId(repoId)
            }
        }
    }

    override suspend fun clearPagination() {
        paginationDao.deleteAllPokemon()
    }

    override suspend fun clearRemoteKeys() {
        remoteKeysDao.clearRemoteKeys()
    }
}