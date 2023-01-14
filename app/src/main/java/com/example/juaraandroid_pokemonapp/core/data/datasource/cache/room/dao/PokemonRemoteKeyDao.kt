package com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonRemoteKeysEntity

/**
 * Created by Ian Damping on 10,June,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Dao
interface PokemonRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(vararg remoteKey: PokemonRemoteKeysEntity)

    @Query("SELECT * FROM remote_keys_pokemon WHERE pokemon_remote_key_id = :pokeId")
    suspend fun remoteKeysRepoId(pokeId: Int): PokemonRemoteKeysEntity?

    @Query("DELETE FROM remote_keys_pokemon")
    suspend fun clearRemoteKeys()
}