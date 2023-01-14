package com.example.juaraandroid_pokemonapp.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juaraandroid_pokemonapp.core.domain.common.DomainResult
import com.example.juaraandroid_pokemonapp.core.domain.usecase.PokemonUseCase
import com.example.juaraandroid_pokemonapp.feature.state.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: PokemonUseCase) : ViewModel() {

    private val _state: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState.initial())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            when (val data = useCase.getPokemon()) {
                is DomainResult.Content -> _state.update { currentUiState ->
                    currentUiState.copy(isLoading = false, data = data.data)
                }
                is DomainResult.Error -> _state.update { currentUiState ->
                    currentUiState.copy(isLoading = false, failedMessage = data.message)
                }
            }
        }
    }
}
