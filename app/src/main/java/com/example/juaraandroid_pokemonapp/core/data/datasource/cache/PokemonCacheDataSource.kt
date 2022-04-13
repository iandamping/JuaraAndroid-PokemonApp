package com.example.juaraandroid_pokemonapp.core.data.datasource.cache

import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.PokemonFavoriteEntity
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonCacheDataSource {
    suspend fun clearFavorite(id: Int)

    suspend fun saveFavorite(data: PokemonDetail)

    fun getListFavorite(): Flow<List<PokemonFavoriteEntity>>

}