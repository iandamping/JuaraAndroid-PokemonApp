package com.example.juaraandroid_pokemonapp.core.data.datasource.cache

import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.PokemonFavoriteDao
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.PokemonFavoriteEntity
import com.example.juaraandroid_pokemonapp.core.domain.model.mapToFavoriteDatabase
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonCacheDataSourceImpl @Inject constructor(
    private val favoriteDao: PokemonFavoriteDao,
) : PokemonCacheDataSource {
    override suspend fun clearFavorite(id: Int) {
        favoriteDao.deleteFavoritePokemonById(id)
    }

    override suspend fun saveFavorite(data: PokemonDetail) {
        favoriteDao.insertPokemon(data.mapToFavoriteDatabase())
    }

    override fun getListFavorite(): Flow<List<PokemonFavoriteEntity>> {
        return favoriteDao.loadPokemon()
    }
}