package com.example.juaraandroid_pokemonapp.feature.state

import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import javax.annotation.concurrent.Immutable

@Immutable
data class SearchPokemonState(
    val failedMessage: String,
    val data: List<PokemonDetail>
) {
    companion object {
        fun initial() = SearchPokemonState(
            failedMessage = "",
            data = emptyList()
        )
    }
}
