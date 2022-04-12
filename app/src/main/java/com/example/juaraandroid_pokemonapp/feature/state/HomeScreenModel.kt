package com.example.juaraandroid_pokemonapp.feature.state

import com.example.juaraandroid_pokemonapp.core.domain.response.PokemonDetail
import javax.annotation.concurrent.Immutable


/**
 * Created by Ian Damping on 21,October,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */

@Immutable
data class HomeScreenState(
    val isLoading: Boolean,
    val failedMessage: String,
    val data: List<PokemonDetail>,
) {

    companion object {
        fun initial() = HomeScreenState(
            isLoading = true,
            failedMessage = "",
            data = emptyList()
        )
    }

    override fun toString(): String {
        return "isLoading: $isLoading, data.size: ${data.size}"
    }
}