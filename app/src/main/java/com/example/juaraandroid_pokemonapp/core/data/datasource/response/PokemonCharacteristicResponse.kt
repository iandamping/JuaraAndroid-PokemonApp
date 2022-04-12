package com.example.juaraandroid_pokemonapp.core.data.datasource.response

import com.squareup.moshi.Json


data class PokemonCharacteristicResponse(
    @field:Json(name ="descriptions") val descriptions: List<ItemPokemonCharacteristicResponse>
)

data class ItemPokemonCharacteristicResponse(
    @field:Json(name = "description") val description: String,
)