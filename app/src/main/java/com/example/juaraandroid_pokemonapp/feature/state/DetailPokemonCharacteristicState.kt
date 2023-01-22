package com.example.juaraandroid_pokemonapp.feature.state

import javax.annotation.concurrent.Immutable

@Immutable
data class DetailPokemonCharacteristicState(
    val isLoading: Boolean,
    val failedMessage: String,
    val characteristic: String,
) {
    companion object {
        fun initial() = DetailPokemonCharacteristicState(
            isLoading = true,
            failedMessage = "",
            characteristic = ""
        )
    }
}
