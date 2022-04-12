package com.example.juaraandroid_pokemonapp.core.data.datasource.response

import com.squareup.moshi.Json


data class PokemonAreasResponse(
    @field:Json(name = "location_area") val area: PokemonAreasName
)

data class PokemonAreasName(
    @field:Json(name = "name") val name: String
)
