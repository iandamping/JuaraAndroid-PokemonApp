package com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys_pokemon")
data class PokemonRemoteKeysEntity(
    @PrimaryKey
    @ColumnInfo(name = "pokemon_remote_key_id") val pokeId: Int,
    @ColumnInfo(name = "pokemon_remote_prev_key") val prevKey: Int?,
    @ColumnInfo(name = "pokemon_remote_next_key") val nextKey: Int?
)
