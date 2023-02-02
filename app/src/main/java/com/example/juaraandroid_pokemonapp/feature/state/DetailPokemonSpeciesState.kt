package com.example.juaraandroid_pokemonapp.feature.state

import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetailSpecies
import javax.annotation.concurrent.Immutable

@Immutable
data class DetailPokemonSpeciesState(
    val isLoading: Boolean,
    val failedMessage: String,
    val data: PokemonDetailSpecies?,
) {
    companion object {
        fun initial() = DetailPokemonSpeciesState(
            isLoading = true,
            failedMessage = "",
            data = null
        )
    }

}