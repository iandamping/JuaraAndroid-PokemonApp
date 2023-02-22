package com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonQuizEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonQuizDao {

    @Query("SELECT * FROM quiz_pokemon")
    fun loadQuizPokemon(): Flow<List<PokemonQuizEntity>>

    @Query("SELECT * FROM quiz_pokemon WHERE pokemon_id = :id")
    fun loadQuizPokemonById(id: Int?): Flow<PokemonQuizEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(vararg data: PokemonQuizEntity)

    @Query("DELETE FROM quiz_pokemon")
    suspend fun deleteAllQuizPokemon()

}