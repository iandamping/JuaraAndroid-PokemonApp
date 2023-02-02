package com.example.juaraandroid_pokemonapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import com.example.juaraandroid_pokemonapp.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: PokemonUseCase) : ViewModel() {

    private val _quizState: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val quizState: StateFlow<Boolean> = _quizState.asStateFlow()

    val pokemonPagination: Flow<PagingData<PokemonDetail>> =
        useCase.getPaginationPokemon()
            .cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            useCase.getListOfQuiz().collect { listOfQuiz ->
                if (listOfQuiz.isNotEmpty()) {
                    if (listOfQuiz.size > 29) {
                        _quizState.value = true
                    }

                } else _quizState.value = false
            }
        }
    }
}
