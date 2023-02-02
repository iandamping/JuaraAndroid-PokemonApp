package com.example.juaraandroid_pokemonapp.core.data.datasource.cache

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonPaginationEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonQuizEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonRemoteKeysEntity
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonCacheDataSource {

    suspend fun <T> databaseTransaction(block: suspend () -> T): T

    suspend fun clearPagination()

    suspend fun clearRemoteKeys()

    suspend fun clearQuiz()

    suspend fun savePagination(data: List<PokemonDetail>)

    suspend fun saveQuiz(data: List<PokemonDetail>)

    suspend fun saveRemoteKeys(data: List<PokemonRemoteKeysEntity>)

    fun getPagination(): PagingSource<Int, PokemonPaginationEntity>

    fun getPokemonQuiz(): Flow<List<PokemonQuizEntity>>

    fun getSinglePokemonQuiz(id: Int): Flow<PokemonQuizEntity?>

    suspend fun getRemoteKeys(data: Int): PokemonRemoteKeysEntity?

    suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity?

    suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity?

    suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PokemonPaginationEntity>): PokemonRemoteKeysEntity?


}