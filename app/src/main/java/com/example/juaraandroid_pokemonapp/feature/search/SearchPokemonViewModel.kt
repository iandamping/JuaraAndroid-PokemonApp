package com.example.juaraandroid_pokemonapp.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant
import com.example.juaraandroid_pokemonapp.core.domain.usecase.PokemonUseCase
import com.example.juaraandroid_pokemonapp.feature.state.SearchPokemonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class SearchPokemonViewModel @Inject constructor(private val useCase: PokemonUseCase) :
    ViewModel() {
    private val _searchPokemon: MutableStateFlow<String> = MutableStateFlow("")
    val searchPokemon: StateFlow<String> = _searchPokemon.asStateFlow()


    private val _uiState: MutableStateFlow<SearchPokemonState> =
        MutableStateFlow(SearchPokemonState.initial())
    val uiState: StateFlow<SearchPokemonState> = _uiState.asStateFlow()

    fun setSearchPokemon(query: String) {
        _searchPokemon.value = query
    }

    init {
        viewModelScope.launch {
            useCase.getListOfQuiz().combine(searchPokemon) { pokemon, query ->
                Pair(pokemon, query)
            }.collectLatest {
                if (it.first.isNotEmpty()) {
                    if (it.second.isEmpty()) {
                        _uiState.update { currentUiState ->
                            currentUiState.copy(data = it.first, failedMessage = "")
                        }
                    } else {
                        if (it.first.any { listData ->
                                listData.pokemonName.lowercase(Locale.getDefault())
                                    .contains(it.second)
                            }) {
                            _uiState.update { currentUiState ->
                                currentUiState.copy(data = it.first.filter { filter ->
                                    filter.pokemonName.lowercase(Locale.getDefault())
                                        .contains(it.second).apply {
                                        }
                                })
                            }
                        } else {
                            _uiState.update { currentUiState ->
                                currentUiState.copy(
                                    data = emptyList(),
                                    failedMessage = NetworkConstant.EMPTY_DATA
                                )
                            }
                        }
                    }
                } else {
                    _uiState.update { currentUiState ->
                        currentUiState.copy(
                            data = emptyList(),
                            failedMessage = NetworkConstant.EMPTY_DATA
                        )
                    }
                }
            }
        }
    }

}