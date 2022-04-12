package com.example.juaraandroid_pokemonapp.core.data.datasource.response

import com.squareup.moshi.Json


/**
 * Created by Ian Damping on 08,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
data class PokemonTypesResponse(
    @Json(name = "type") val type: PokemonTypeSingleResponse,
)

data class PokemonTypeSingleResponse(
    @Json(name = "name") val typeName: String
)