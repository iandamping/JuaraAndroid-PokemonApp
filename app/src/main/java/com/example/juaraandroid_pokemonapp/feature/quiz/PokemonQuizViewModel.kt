package com.example.juaraandroid_pokemonapp.feature.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant.EMPTY_DATA
import com.example.juaraandroid_pokemonapp.core.domain.usecase.PokemonUseCase
import com.example.juaraandroid_pokemonapp.feature.state.QuizPokemonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonQuizViewModel @Inject constructor(private val useCase: PokemonUseCase) : ViewModel() {

    private val _state: MutableStateFlow<QuizPokemonState> =
        MutableStateFlow(QuizPokemonState.initial())
    val state: StateFlow<QuizPokemonState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            useCase.getListOfQuiz().collect { listOfQuiz ->
                if (listOfQuiz.isNotEmpty()) {
                    _state.update { currentUiState ->
                        currentUiState.copy(data = listOfQuiz.shuffled().take(30))
                    }
                } else _state.update { currentUiState ->
                    currentUiState.copy(failedMessage = EMPTY_DATA)
                }
            }
        }
    }
}