package com.example.juaraandroid_pokemonapp.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juaraandroid_pokemonapp.core.data.datasource.cache.room.entity.PokemonFavoriteEntity
import com.example.juaraandroid_pokemonapp.core.domain.common.DomainResult
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import com.example.juaraandroid_pokemonapp.core.domain.usecase.PokemonUseCase
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonAreaState
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonCharacteristicState
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonSpeciesState
import com.example.juaraandroid_pokemonapp.feature.state.DetailPokemonStatState
import com.example.juaraandroid_pokemonapp.navigation.PokemonNavigationArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: PokemonUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val detailPokemonId =
        savedStateHandle.getStateFlow(PokemonNavigationArgument.DetailPokemonId.name, 0)

    private val _detailStatState: MutableStateFlow<DetailPokemonStatState> =
        MutableStateFlow(DetailPokemonStatState.initial())
    val detailStatState: StateFlow<DetailPokemonStatState> = _detailStatState.asStateFlow()

    private val _detailCharacteristicState: MutableStateFlow<DetailPokemonCharacteristicState> =
        MutableStateFlow(DetailPokemonCharacteristicState.initial())
    val detailCharacteristicState: StateFlow<DetailPokemonCharacteristicState> =
        _detailCharacteristicState.asStateFlow()

    private val _detailAreaState: MutableStateFlow<DetailPokemonAreaState> =
        MutableStateFlow(DetailPokemonAreaState.initial())
    val detailAreaState: StateFlow<DetailPokemonAreaState> = _detailAreaState.asStateFlow()

    private val _detailSpeciesState: MutableStateFlow<DetailPokemonSpeciesState> =
        MutableStateFlow(DetailPokemonSpeciesState.initial())
    val detailSpeciesState: StateFlow<DetailPokemonSpeciesState> = _detailSpeciesState.asStateFlow()


    private val _bookmarkState = MutableStateFlow(false)
    val bookmarkState = _bookmarkState.asStateFlow()

    fun listFavorite(): Flow<List<PokemonFavoriteEntity>> = useCase.getListFavorite()

    fun setBookmarkState(data: Boolean) {
        _bookmarkState.value = data
    }

    init {
        viewModelScope.launch {
            detailPokemonId.collect {
                listOf<Job>(
                    launch { getPokemonById(it) },
                    launch { getPokemonCharacteristicById(it) },
                    launch { getPokemonLocationAreaById(it) },
                    launch { getPokemonSpeciesById(it) }).joinAll()
            }
        }
    }

    private suspend fun getPokemonById(id: Int) {
        when (val data = useCase.getPokemonById(id)) {
            is DomainResult.Content -> _detailStatState.update { currentUiState ->
                currentUiState.copy(isLoading = false, data = data.data)
            }
            is DomainResult.Error -> _detailStatState.update { currentUiState ->
                currentUiState.copy(isLoading = false, failedMessage = data.message)
            }
        }
    }

    private suspend fun getPokemonCharacteristicById(id: Int) {
        when (val data = useCase.getDetailPokemonCharacteristic(id)) {
            is DomainResult.Content -> _detailCharacteristicState.update { currentUiState ->
                currentUiState.copy(isLoading = false, characteristic = data.data)
            }
            is DomainResult.Error -> _detailCharacteristicState.update { currentUiState ->
                currentUiState.copy(isLoading = false, failedMessage = data.message)
            }
        }
    }

    private suspend fun getPokemonLocationAreaById(id: Int) {
        when (val data = useCase.getPokemonLocationAreas(id)) {
            is DomainResult.Content -> _detailAreaState.update { currentUiState ->
                currentUiState.copy(isLoading = false, data = data.data.take(4))
            }
            is DomainResult.Error -> _detailAreaState.update { currentUiState ->
                currentUiState.copy(isLoading = false, failedMessage = data.message)
            }
        }
    }

    private suspend fun getPokemonSpeciesById(id: Int) {
        when (val data = useCase.getDetailSpeciesPokemon(id)) {
            is DomainResult.Content -> _detailSpeciesState.update { currentUiState ->
                currentUiState.copy(isLoading = false, data = data.data)
            }
            is DomainResult.Error -> _detailSpeciesState.update { currentUiState ->
                currentUiState.copy(isLoading = false, failedMessage = data.message)
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