package com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.dao.PokemonFavoriteDao
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.dao.PokemonRemoteKeyDao
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonFavoriteEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonPaginationEntity
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonRemoteKeysEntity
import com.spesolution.myapplication.core.data.datasource.cache.room.PokemonPaginationDao

/**
 * Created by Ian Damping on 10,June,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Database(
    entities = [PokemonPaginationEntity::class, PokemonFavoriteEntity::class, PokemonRemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun paginationDao(): PokemonPaginationDao
    abstract fun favoriteDao(): PokemonFavoriteDao
    abstract fun remoteKeyDao(): PokemonRemoteKeyDao
}