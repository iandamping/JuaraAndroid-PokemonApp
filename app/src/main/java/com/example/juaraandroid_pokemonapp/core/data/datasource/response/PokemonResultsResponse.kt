package com.example.juaraandroid_pokemonapp.core.data.datasource.response

import com.squareup.moshi.Json


data class PokemonResultsResponse(
    @Json(name = "url") val pokemonUrl: String
)