package com.example.juaraandroid_pokemonapp.core.data.datasource.response

import com.squareup.moshi.Json


/**
 * Created by Ian Damping on 08,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
data class PokemonSpritesResponse(
    @Json(name = "other") val sprites: PokemonSpritesOtherResponse,
    @Json(name = "back_default") val smallImage1: String,
    @Json(name = "back_shiny") val smallImage2: String,
    @Json(name = "front_default") val smallImage3: String,
    @Json(name = "front_shiny") val smallImage4: String,
)

data class PokemonSpritesOtherResponse(@Json(name = "official-artwork") val other: PokemonOfficialArtworkResponse)

data class PokemonOfficialArtworkResponse(@Json(name = "front_default") val image: String)

