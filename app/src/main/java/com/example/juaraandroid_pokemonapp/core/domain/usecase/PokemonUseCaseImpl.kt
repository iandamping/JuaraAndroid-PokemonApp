package com.example.juaraandroid_pokemonapp.core.domain.usecase

import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.PokemonFavoriteEntity
import com.example.juaraandroid_pokemonapp.core.domain.model.DomainResult
import com.example.juaraandroid_pokemonapp.core.domain.model.UiState
import com.example.juaraandroid_pokemonapp.core.domain.repository.PokemonRepository
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetail
import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetailSpecies
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

    override fun getPokemon(): Flow<UiState<List<PokemonDetail>>> {
        return repository.getPokemon().map {
            when (it) {
                is DomainResult.Error -> UiState.Error(it.message)
                is DomainResult.Content -> UiState.Content(it.data)
            }
        }
    }

    override fun getDetailSpeciesPokemon(url: String): Flow<PokemonDetailSpecies> {
        return repository.getDetailSpeciesPokemon(url = url)
    }

    override fun getDetailPokemonCharacteristic(id: Int): Flow<UiState<String>> {
        return repository.getDetailPokemonCharacteristic(id).map {
            when (it) {
                is DomainResult.Error -> UiState.Error(it.message)
                is DomainResult.Content -> UiState.Content(it.data)
            }
        }
    }

    override fun getPokemonLocationAreas(id: Int): Flow<UiState<List<String>>> {
        return repository.getPokemonLocationAreas(id).map {
            when (it) {
                is DomainResult.Error -> UiState.Error(it.message)
                is DomainResult.Content -> UiState.Content(it.data)
            }
        }
    }

    override fun getPokemonById(id: Int): Flow<UiState<PokemonDetail>> {
        return repository.getPokemonById(id).map {
            when (it) {
                is DomainResult.Error -> UiState.Error(it.message)
                is DomainResult.Content -> UiState.Content(it.data)
            }
        }
    }

    override suspend fun saveFavorite(data: PokemonDetail) {
        repository.saveFavorite(data)
    }

    override suspend fun clearFavorite(id: Int) {
        repository.clearFavorite(id)
    }

    override fun getListFavorite(): Flow<List<PokemonFavoriteEntity>> {
        return repository.getListFavorite()
    }

}