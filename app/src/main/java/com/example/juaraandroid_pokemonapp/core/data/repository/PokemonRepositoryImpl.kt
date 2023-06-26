package com.example.juaraandroid_pokemonapp.core.data.repository

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.juaraandroid_pokemonapp.core.data.datasource.PokemonRemoteDataSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.PokemonCacheDataSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonPaginationEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonRemoteKeysEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.NetworkConstant
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.NetworkConstant.DEFAULT_ERROR
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.NetworkConstant.EMPTY_DATA
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.NetworkConstant.POKEMON_STARTING_OFFSET
import com.example.juaraandroid_pokemonapp.core.domain.common.DataSourceResult
import com.example.juaraandroid_pokemonapp.core.domain.common.DomainResult
import com.example.juaraandroid_pokemonapp.core.domain.common.mapListToDetail
import com.example.juaraandroid_pokemonapp.core.domain.common.mapToDetail
import com.example.juaraandroid_pokemonapp.core.domain.common.mapToSpeciesDetail
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetailSpecies
import com.example.juaraandroid_pokemonapp.core.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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
                try {
                    val data = withContext(Dispatchers.Default) {
                        result.data.map { singleItem ->
                            remoteDataSource.getDetailPokemon(singleItem.pokemonUrl).mapToDetail()
                        }
                    }

                    if (data.isEmpty()) {
                        DomainResult.Error(EMPTY_DATA)
                    } else DomainResult.Content(data)
                } catch (e: Exception) {
                    DomainResult.Error(e.message ?: EMPTY_DATA)
                }

            }
            is DataSourceResult.SourceError -> {
                DomainResult.Error("$DEFAULT_ERROR ${result.exception.message}")
            }
        }
    }

    override suspend fun getEvolvingPokemon(url: String): DomainResult<PokemonDetail> {
        return try {
            val cacheResult = cacheDataSource.getPokemonQuiz().map { it.mapListToDetail() }.first()
            val remoteResult = remoteDataSource.getPokemonEvolution(url)
            val mapCacheResult = cacheResult.firstOrNull {
                it.pokemonName.equals(
                    remoteResult.eggName,
                    ignoreCase = true
                )
            }
            if (mapCacheResult != null) {
                DomainResult.Content(mapCacheResult)
            } else {
                when (val getPokemonFromRemote =
                    remoteDataSource.getPokemonByName(remoteResult.eggName)) {
                    is DataSourceResult.SourceError -> DomainResult.Error(
                        getPokemonFromRemote.exception.message ?: EMPTY_DATA
                    )
                    is DataSourceResult.SourceValue -> DomainResult.Content(getPokemonFromRemote.data.mapToDetail())
                }
            }

        } catch (e: Exception) {
            DomainResult.Error(e.message ?: EMPTY_DATA)
        }

    }

    override suspend fun getSimilarEggGroupPokemon(url: String): DomainResult<List<PokemonDetail>> {
        return try {
            val cacheResult = cacheDataSource.getPokemonQuiz().map { it.mapListToDetail() }.first()
            val remoteResultName =
                remoteDataSource.getPokemonEggGroup(url).shuffled().take(4).map { it.name }
            val mapCacheResult = remoteResultName.flatMap { pokemonName ->
                cacheResult.filter { it.pokemonName.equals(pokemonName, ignoreCase = true) }
            }
            if (mapCacheResult.isEmpty()) {
                val getPokemonFromRemote = withContext(Dispatchers.Default) {
                    remoteResultName.map { singleItem ->
                            remoteDataSource.getDetailPokemonDirectByName(singleItem)
                                .mapToDetail()
                    }
                }

                if (getPokemonFromRemote.isNotEmpty()) {
                    DomainResult.Content(getPokemonFromRemote)
                } else DomainResult.Error(EMPTY_DATA)
            } else {
                DomainResult.Content(mapCacheResult)
            }

        } catch (e: Exception) {
            DomainResult.Error(e.message ?: EMPTY_DATA)
        }
    }

    override fun getPaginationPokemonRemoteMediator(): RemoteMediator<Int, PokemonPaginationEntity> {
        return object : RemoteMediator<Int, PokemonPaginationEntity>() {

            override suspend fun initialize(): InitializeAction {
                return InitializeAction.LAUNCH_INITIAL_REFRESH
            }

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
                            val paginationData = withContext(Dispatchers.Default) {
                                apiResponse.data.map { singleItem ->
                                        remoteDataSource.getDetailPokemon(singleItem.pokemonUrl)
                                            .mapToDetail()
                                }
                            }
                            val endOfPaginationReached = paginationData.isEmpty()
                            cacheDataSource.databaseTransaction {
                                // clear all tables in the database
                                if (loadType == LoadType.REFRESH) {
                                    with(cacheDataSource) {
                                        clearRemoteKeys()
                                        clearPagination()
                                        clearQuiz()
                                    }
                                }
                                val prevKey =
                                    if (page == POKEMON_STARTING_OFFSET) null else page - 1
                                val nextKey = if (endOfPaginationReached) null else page + 1
                                val keys = paginationData.map {
                                    PokemonRemoteKeysEntity(
                                        pokeId = it.pokemonId,
                                        prevKey = prevKey,
                                        nextKey = nextKey
                                    )
                                }
                                with(cacheDataSource) {
                                    saveRemoteKeys(keys)
                                    savePagination(paginationData)
                                    saveQuiz(paginationData)
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

    override suspend fun getDetailSpeciesPokemon(id: Int): DomainResult<PokemonDetailSpecies> {
        return when (val result = remoteDataSource.getDetailSpeciesPokemon(id)) {
            is DataSourceResult.SourceValue -> {
                DomainResult.Content(result.data.mapToSpeciesDetail())
            }
            is DataSourceResult.SourceError -> {
                DomainResult.Error("$DEFAULT_ERROR ${result.exception.message}")
            }
        }
    }

    override suspend fun getDetailPokemonCharacteristic(id: Int): DomainResult<String> {
        return when (val result = remoteDataSource.getDetailPokemonCharacteristic(id)) {
            is DataSourceResult.SourceValue -> {
                DomainResult.Content(result.data)
            }
            is DataSourceResult.SourceError -> {
                DomainResult.Error("$DEFAULT_ERROR ${result.exception.message}")
            }
        }
    }

    override suspend fun getPokemonLocationAreas(id: Int): DomainResult<List<String>> {
        return when (val result = remoteDataSource.getPokemonLocationAreas(id)) {
            is DataSourceResult.SourceValue -> {
                DomainResult.Content(result.data)
            }
            is DataSourceResult.SourceError -> {
                DomainResult.Error("$DEFAULT_ERROR ${result.exception.message}")
            }
        }
    }

    override suspend fun getPokemonById(id: Int): DomainResult<PokemonDetail> {
        return when (val result = remoteDataSource.getPokemonById(id)) {
            is DataSourceResult.SourceValue -> {
                DomainResult.Content(result.data.mapToDetail())
            }
            is DataSourceResult.SourceError -> {
                DomainResult.Error("$DEFAULT_ERROR ${result.exception.message}")
            }
        }
    }

    override fun getListOfQuiz(): Flow<List<PokemonDetail>> {
        return cacheDataSource.getPokemonQuiz().map { it.mapListToDetail() }
    }


}

