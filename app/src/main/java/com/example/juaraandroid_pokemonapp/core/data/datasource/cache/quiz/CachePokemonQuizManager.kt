package com.example.juaraandroid_pokemonapp.core.data.datasource.cache.quiz

import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonQuizEntity
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface CachePokemonQuizManager {

    suspend fun saveQuiz(data: List<PokemonDetail>)

    fun getPokemonQuiz(): Flow<List<PokemonQuizEntity>>

    fun getSinglePokemonQuiz(id: Int): Flow<PokemonQuizEntity?>

    suspend fun clearQuiz()
}