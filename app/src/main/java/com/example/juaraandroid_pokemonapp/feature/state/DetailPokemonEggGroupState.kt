package com.example.juaraandroid_pokemonapp.feature.state

import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import javax.annotation.concurrent.Immutable

@Immutable
data class DetailPokemonEggGroupState(
    val isLoading: Boolean,
    val failedMessage: String,
    val data: List<PokemonDetail>,
) {
    companion object {
        fun initial() = DetailPokemonEggGroupState(
            isLoading = true,
            failedMessage = "",
            data = emptyList()
        )
    }
}
