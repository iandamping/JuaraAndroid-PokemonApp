package com.example.juaraandroid_pokemonapp.core.data.datasource.cache

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.PokemonDatabase
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.dao.PokemonFavoriteDao
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.dao.PokemonQuizDao
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.dao.PokemonRemoteKeyDao
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonFavoriteEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonPaginationEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonQuizEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonRemoteKeysEntity
import com.example.juaraandroid_pokemonapp.core.domain.common.mapToFavoriteDatabase
import com.example.juaraandroid_pokemonapp.core.domain.common.mapToPaginationDatabase
import com.example.juaraandroid_pokemonapp.core.domain.common.mapToQuizDatabase
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import com.spesolution.myapplication.core.data.datasource.cache.room.PokemonPaginationDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonCacheDataSourceImpl @Inject constructor(
    private val db: PokemonDatabase,
    private val favoriteDao: PokemonFavoriteDao,
    private val quizDao: PokemonQuizDao,
    private val paginationDao: PokemonPaginationDao,
    private val remoteKeysDao: PokemonRemoteKeyDao
) : PokemonCacheDataSource {

    override suspend fun <T> databaseTransaction(block: suspend () -> T): T {
        return db.withTransaction {
            block.invoke()
        }
    }

    override suspend fun clearFavorite(id: Int) {
        favoriteDao.deleteFavoritePokemonById(id)
    }

    override suspend fun clearPagination() {
        paginationDao.deleteAllPokemon()
    }

    override suspend fun clearRemoteKeys() {
        remoteKeysDao.clearRemoteKeys()
    }

    override suspend fun clearQuiz() {
        quizDao.deleteAllQuizPokemon()
    }

    override suspend fun saveFavorite(data: PokemonDetail) {
        favoriteDao.insertPokemon(data.mapToFavoriteDatabase())
    }

    override suspend fun savePagination(data: List<PokemonDetail>) {
        paginationDao.insertPokemon(*data.map { it.mapToPaginationDatabase() }.toTypedArray())
    }

    override suspend fun saveQuiz(data: List<PokemonDetail>) {
        quizDao.insertPokemon(*data.map { it.mapToQuizDatabase() }.toTypedArray())
    }

    override suspend fun saveRemoteKeys(data: List<PokemonRemoteKeysEntity>) {
        remoteKeysDao.insertKey(*data.toTypedArray())
    }

    override fun getListFavorite(): Flow<List<PokemonFavoriteEntity>> {
        return favoriteDao.loadPokemon()
    }

    override fun getPagination(): PagingSource<Int, PokemonPaginationEntity> {
        return paginationDao.loadPokemon()
    }

    override fun getPokemonQuiz(): Flow<List<PokemonQuizEntity>> {
        return quizDao.loadQuizPokemon()
    }

    override fun getSinglePokemonQuiz(id: Int): Flow<PokemonQuizEntity?> {
        return quizDao.loadQuizPokemonById(id)
    }

    override suspend fun getRemoteKeys(data: Int): PokemonRemoteKeysEntity? {
        return remoteKeysDao.remoteKeysRepoId(data)
    }

    override suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { poke ->
            remoteKeysDao.remoteKeysRepoId(poke.pokemonId)
        }
    }

    override suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { poke ->
                remoteKeysDao.remoteKeysRepoId(poke.pokemonId)
            }
    }

    override suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.pokemonId?.let { repoId ->
                remoteKeysDao.remoteKeysRepoId(repoId)
            }
        }
    }
}