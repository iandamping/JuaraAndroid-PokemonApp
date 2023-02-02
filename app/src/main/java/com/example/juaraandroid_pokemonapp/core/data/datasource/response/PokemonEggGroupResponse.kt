package com.example.juaraandroid_pokemonapp.core.data.datasource.response

import com.squareup.moshi.Json

data class PokemonEggGroupResponse(
    @Json(name = "name") val eggGroupName: String,
    @Json(name = "pokemon_species") val eggGroupSpecies: List<ItemPokemonEggResponse>
)

data class ItemPokemonEggResponse(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "url") val url: String
)
