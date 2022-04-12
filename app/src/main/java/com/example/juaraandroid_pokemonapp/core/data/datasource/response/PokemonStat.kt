package com.example.juaraandroid_pokemonapp.core.data.datasource.response

import com.squareup.moshi.Json


/**
 * Created by Ian Damping on 08,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */

data class PokemonBasicStatsResponse(
    @Json(name = "base_stat") val baseStat: Int,
    @Json(name = "stat") val statName: PokemonStatNameResponse
)

data class PokemonStatNameResponse(@Json(name = "name") val name: String)