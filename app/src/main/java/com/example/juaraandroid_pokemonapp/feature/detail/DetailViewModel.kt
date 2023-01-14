package com.example.juaraandroid_pokemonapp.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonFavoriteEntity
import com.example.juaraandroid_pokemonapp.core.domain.common.DomainResult
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import com.example.juaraandroid_pokemonapp.core.domain.usecase.PokemonUseCase
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonStatState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val useCase: PokemonUseCase) : ViewModel() {

    private val _detailState: MutableStateFlow<DetailPokemonStatState> =
        MutableStateFlow(DetailPokemonStatState.initial())
    val detailState: StateFlow<DetailPokemonStatState> = _detailState.asStateFlow()

    private val _bookmarkState = MutableStateFlow(false)
    val bookmarkState = _bookmarkState.asStateFlow()

    private val _selectedPokemonId = MutableStateFlow(0)
    val selectedPokemonId = _selectedPokemonId.asStateFlow()

    fun listFavorite(): Flow<List<PokemonFavoriteEntity>> = useCase.getListFavorite()

    fun setSelectedPokemonId(id: Int) {
        _selectedPokemonId.value = id
    }

    fun setBookmarkState(data: Boolean) {
        _bookmarkState.value = data
    }

    init {
        viewModelScope.launch {
            selectedPokemonId.collect {
                when (val data = useCase.getPokemonById(it)) {
                    is DomainResult.Content -> _detailState.update { currentUiState ->
                        currentUiState.copy(isLoading = false, data = data.data)
                    }
                    is DomainResult.Error -> _detailState.update { currentUiState ->
                        currentUiState.copy(isLoading = false, failedMessage = data.message)
                    }
                }
            }
        }
    }

    fun saveFavorite(data: PokemonDetail) {
        viewModelScope.launch {
            useCase.saveFavorite(data)
        }
    }

    fun clearFavorite(id: Int) {
        viewModelScope.launch {
            useCase.clearFavorite(id)
        }
    }

}