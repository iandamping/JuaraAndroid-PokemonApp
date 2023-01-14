package com.example.juaraandroid_pokemonapp.feature.state


import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import javax.annotation.concurrent.Immutable

@Immutable
data class DetailPokemonStatState(
    val isLoading: Boolean,
    val failedMessage: String,
    val data: PokemonDetail?,
) {
    companion object {
        fun initial() = DetailPokemonStatState(
            isLoading = true,
            failedMessage = "",
            data = null
        )
    }

    override fun toString(): String {
        return "isLoading: $isLoading, data: $data"
    }
}