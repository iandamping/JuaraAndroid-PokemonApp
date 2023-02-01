package com.example.juaraandroid_pokemonapp.feature.state

import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import javax.annotation.concurrent.Immutable

@Immutable
data class DetailPokemonSpeciesEvolutionState(
    val isLoading: Boolean,
    val failedMessage: String,
    val data: PokemonDetail?
) {
    companion object {
        fun initial() = DetailPokemonSpeciesEvolutionState(
            isLoading = true,
            failedMessage = "",
            data = null
        )
    }
}
