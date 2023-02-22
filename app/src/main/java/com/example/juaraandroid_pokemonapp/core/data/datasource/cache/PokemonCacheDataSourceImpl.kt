package com.example.juaraandroid_pokemonapp.core.data.datasource.cache

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.pagination.CachePokemonPaginationManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.quiz.CachePokemonQuizManager
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.PokemonDatabase
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonPaginationEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonQuizEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonRemoteKeysEntity
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonCacheDataSourceImpl @Inject constructor(
    private val db: PokemonDatabase,
    private val quizManager: CachePokemonQuizManager,
    private val paginationManager: CachePokemonPaginationManager
) : PokemonCacheDataSource {

    override suspend fun <T> databaseTransaction(block: suspend () -> T): T {
        return db.withTransaction {
            block.invoke()
        }
    }

    override suspend fun clearPagination() {
        paginationManager.clearPagination()
    }

    override suspend fun clearRemoteKeys() {
        paginationManager.clearRemoteKeys()
    }

    override suspend fun clearQuiz() {
        quizManager.clearQuiz()
    }

    override suspend fun savePagination(data: List<PokemonDetail>) {
        paginationManager.savePagination(data)
    }

    override suspend fun saveQuiz(data: List<PokemonDetail>) {
        quizManager.saveQuiz(data)
    }

    override suspend fun saveRemoteKeys(data: List<PokemonRemoteKeysEntity>) {
        paginationManager.saveRemoteKeys(data)
    }

    override fun getPagination(): PagingSource<Int, PokemonPaginationEntity> {
        return paginationManager.getPagination()
    }

    override fun getPokemonQuiz(): Flow<List<PokemonQuizEntity>> {
        return quizManager.getPokemonQuiz()
    }

    override fun getSinglePokemonQuiz(id: Int): Flow<PokemonQuizEntity?> {
        return quizManager.getSinglePokemonQuiz(id)
    }

    override suspend fun getRemoteKeys(data: Int): PokemonRemoteKeysEntity? {
        return paginationManager.getRemoteKeys(data)
    }

    override suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity? {
        return paginationManager.getRemoteKeyForLastItem(state = state)

    }

    override suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity? {
        return paginationManager.getRemoteKeyForFirstItem(state = state)
    }

    override suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity? {
        return paginationManager.getRemoteKeyClosestToCurrentPosition(state = state)
    }
}