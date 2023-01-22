package com.example.juaraandroid_pokemonapp.feature.state

import javax.annotation.concurrent.Immutable

@Immutable
data class DetailPokemonAreaState(
    val isLoading: Boolean,
    val failedMessage: String,
    val data: List<String>,
) {
    companion object {
        fun initial() = DetailPokemonAreaState(
            isLoading = true,
            failedMessage = "",
            data = emptyList()
        )
    }
}
