package com.example.juaraandroid_pokemonapp.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import com.example.juaraandroid_pokemonapp.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokemonPaginationViewModel @Inject constructor(useCase: PokemonUseCase) :
    ViewModel() {

    val pokemonPagination: Flow<PagingData<PokemonDetail>> =
        useCase.getPaginationPokemon()
            .cachedIn(viewModelScope)

}