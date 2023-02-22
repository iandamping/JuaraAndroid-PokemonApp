package com.example.juaraandroid_pokemonapp.core.data.datasource.cache.quiz

import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.dao.PokemonQuizDao
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonQuizEntity
import com.example.juaraandroid_pokemonapp.core.domain.common.mapToQuizDatabase
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CachePokemonQuizManagerImpl @Inject constructor(
    private val quizDao: PokemonQuizDao,
) : CachePokemonQuizManager {

    override suspend fun saveQuiz(data: List<PokemonDetail>) {
        quizDao.insertPokemon(*data.map { it.mapToQuizDatabase() }.toTypedArray())
    }

    override fun getPokemonQuiz(): Flow<List<PokemonQuizEntity>> {
        return quizDao.loadQuizPokemon()
    }

    override fun getSinglePokemonQuiz(id: Int): Flow<PokemonQuizEntity?> {
        return quizDao.loadQuizPokemonById(id)
    }

    override suspend fun clearQuiz() {
        quizDao.deleteAllQuizPokemon()
    }
}