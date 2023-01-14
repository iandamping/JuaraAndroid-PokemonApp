package com.example.juaraandroid_pokemonapp.core.data.repository

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.juaraandroid_pokemonapp.core.data.datasource.PokemonRemoteDataSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.PokemonCacheDataSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonFavoriteEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonPaginationEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonRemoteKeysEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.NetworkConstant
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.NetworkConstant.EMPTY_DATA
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.NetworkConstant.NETWORK_ERROR
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.NetworkConstant.POKEMON_STARTING_OFFSET
import com.example.juaraandroid_pokemonapp.core.domain.common.DataSourceResult
import com.example.juaraandroid_pokemonapp.core.domain.common.DomainResult
import com.example.juaraandroid_pokemonapp.core.domain.common.mapToDetail
import com.example.juaraandroid_pokemonapp.core.domain.common.mapToSpeciesDetail
import com.example.juaraandroid_pokemonapp.core.domain.repository.PokemonRepository
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetailSpecies
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val cacheDataSource: PokemonCacheDataSource
) :
    PokemonRepository {

    override suspend fun getPokemon(): DomainResult<List<PokemonDetail>> {
        return when (val result = remoteDataSource.getPokemon()) {
            is DataSourceResult.SourceValue -> {
                val data = result.data.map { singleItem ->
                    remoteDataSource.getDetailPokemon(singleItem.pokemonUrl).mapToDetail()
                }
                if (data.isEmpty()) {
                    DomainResult.Error(EMPTY_DATA)
                } else DomainResult.Content(data)
            }
            is DataSourceResult.SourceError -> {
                DomainResult.Error(result.exception.message ?: NETWORK_ERROR)
            }
        }
    }

    override fun getPaginationPokemonRemoteMediator(): RemoteMediator<Int, PokemonPaginationEntity> {
        return object : RemoteMediator<Int, PokemonPaginationEntity>() {
            override suspend fun load(
                loadType: LoadType,
                state: PagingState<Int, PokemonPaginationEntity>
            ): MediatorResult {
                val page = when (loadType) {
                    LoadType.REFRESH -> {
                        val remoteKeys = cacheDataSource.getRemoteKeyClosestToCurrentPosition(state)
                        remoteKeys?.nextKey?.minus(1) ?: POKEMON_STARTING_OFFSET
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = cacheDataSource.getRemoteKeyForFirstItem(state)
                        val prevKey = remoteKeys?.prevKey
                            ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                        prevKey
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = cacheDataSource.getRemoteKeyForLastItem(state)
                        val nextKey = remoteKeys?.nextKey
                            ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                        nextKey
                    }

                }

                try {
                    when (val apiResponse =
                        remoteDataSource.getPaginationPokemon(page * NetworkConstant.POKEMON_OFFSET)) {
                        is DataSourceResult.SourceError -> return MediatorResult.Error(apiResponse.exception)
                        is DataSourceResult.SourceValue -> {
                            val data = apiResponse.data.map { singleItem ->
                                remoteDataSource.getDetailPokemon(singleItem.pokemonUrl)
                                    .mapToDetail()
                            }
                            val endOfPaginationReached = data.isEmpty()
                            cacheDataSource.databaseTransaction {
                                // clear all tables in the database
                                if (loadType == LoadType.REFRESH) {
                                    with(cacheDataSource) {
                                        clearRemoteKeys()
                                        clearPagination()
                                    }
                                }
                                val prevKey =
                                    if (page == POKEMON_STARTING_OFFSET) null else page - 1
                                val nextKey = if (endOfPaginationReached) null else page + 1
                                val keys = data.map {
                                    PokemonRemoteKeysEntity(
                                        pokeId = it.pokemonId,
                                        prevKey = prevKey,
                                        nextKey = nextKey
                                    )
                                }
                                with(cacheDataSource) {
                                    saveRemoteKeys(keys)
                                    savePagination(data)
                                }
                            }
                            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                        }
                    }
                } catch (exception: IOException) {
                    return MediatorResult.Error(exception)
                } catch (exception: HttpException) {
                    return MediatorResult.Error(exception)
                }
            }
        }
    }

    override fun getPaginationPokemonPagingSource(): PagingSource<Int, PokemonPaginationEntity> {
        return cacheDataSource.getPagination()
    }

    override suspend fun getDetailSpeciesPokemon(url: String): PokemonDetailSpecies {
        return remoteDataSource.getDetailSpeciesPokemon(url).mapToSpeciesDetail()
    }

    override suspend fun getDetailPokemonCharacteristic(id: Int): DomainResult<String> {
        return when (val result = remoteDataSource.getDetailPokemonCharacteristic(id)) {
            is DataSourceResult.SourceValue -> {
                DomainResult.Content(result.data)
            }
            is DataSourceResult.SourceError -> {
                DomainResult.Error(result.exception.message ?: NETWORK_ERROR)
            }
        }
    }

    override suspend fun getPokemonLocationAreas(id: Int): DomainResult<List<String>> {
        return when (val result = remoteDataSource.getPokemonLocationAreas(id)) {
            is DataSourceResult.SourceValue -> {
                DomainResult.Content(result.data)
            }
            is DataSourceResult.SourceError -> {
                DomainResult.Error(result.exception.message ?: NETWORK_ERROR)
            }
        }
    }

    override suspend fun getPokemonById(id: Int): DomainResult<PokemonDetail> {
        return when (val result = remoteDataSource.getPokemonById(id)) {
            is DataSourceResult.SourceValue -> {
                DomainResult.Content(result.data.mapToDetail())
            }
            is DataSourceResult.SourceError -> {
                DomainResult.Error(result.exception.message ?: NETWORK_ERROR)
            }
        }
    }

    override suspend fun saveFavorite(data: PokemonDetail) {
        cacheDataSource.saveFavorite(data)
    }

    override suspend fun clearFavorite(id: Int) {
        cacheDataSource.clearFavorite(id)
    }

    override fun getListFavorite(): Flow<List<PokemonFavoriteEntity>> {
        return cacheDataSource.getListFavorite()
    }


}

