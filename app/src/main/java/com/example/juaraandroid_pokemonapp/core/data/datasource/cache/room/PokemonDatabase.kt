package com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Ian Damping on 10,June,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Database(
    entities = [PokemonFavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun favoriteDao(): PokemonFavoriteDao
}