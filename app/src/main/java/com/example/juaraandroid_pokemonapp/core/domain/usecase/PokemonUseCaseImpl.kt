package com.example.juaraandroid_pokemonapp.core.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.juaraandroid_pokemonapp.core.domain.common.DomainResult
import com.example.juaraandroid_pokemonapp.core.domain.common.mapToDetail
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetailSpecies
import com.example.juaraandroid_pokemonapp.core.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Ian Damping on 10,June,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PokemonUseCaseImpl @Inject constructor(private val repository: PokemonRepository) :
    PokemonUseCase {

    override suspend fun getPokemon(): DomainResult<List<PokemonDetail>> {
        return repository.getPokemon()
    }

    override suspend fun getEvolvingPokemon(url: String): DomainResult<PokemonDetail> {
        return repository.getEvolvingPokemon(url)
    }

    override suspend fun getSimilarEggGroupPokemon(url: String): DomainResult<List<PokemonDetail>> {
        return repository.getSimilarEggGroupPokemon(url)
    }

    override fun getPaginationPokemon(): Flow<PagingData<PokemonDetail>> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            remoteMediator = repository.getPaginationPokemonRemoteMediator(),
            pagingSourceFactory = { repository.getPaginationPokemonPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.mapToDetail() }
        }
    }

    override suspend fun getDetailSpeciesPokemon(id: Int): DomainResult<PokemonDetailSpecies> {
        return repository.getDetailSpeciesPokemon(id)
    }

    override suspend fun getDetailPokemonCharacteristic(id: Int): DomainResult<String> {
        return repository.getDetailPokemonCharacteristic(id)
    }

    override suspend fun getPokemonLocationAreas(id: Int): DomainResult<List<String>> {
        return repository.getPokemonLocationAreas(id)
    }

    override suspend fun getPokemonById(id: Int): DomainResult<PokemonDetail> {
        return repository.getPokemonById(id)
    }

    override fun getListOfQuiz(): Flow<List<PokemonDetail>> {
        return repository.getListOfQuiz()
    }

}