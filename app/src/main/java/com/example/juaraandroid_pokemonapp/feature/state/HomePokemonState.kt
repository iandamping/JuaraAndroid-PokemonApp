package com.example.juaraandroid_pokemonapp.feature.state

import com.example.juaraandroid_pokemonapp.core.domain.model.PokemonDetail
import javax.annotation.concurrent.Immutable


/**
 * Created by Ian Damping on 21,October,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Immutable
data class HomePokemonState(
    val isLoading: Boolean,
    val failedMessage: String,
    val data: List<PokemonDetail>,
) {
    companion object {
        fun initial() = HomePokemonState(
            isLoading = true,
            failedMessage = "",
            data = emptyList()
        )
    }
}