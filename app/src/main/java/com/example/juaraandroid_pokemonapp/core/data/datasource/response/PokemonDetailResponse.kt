package com.example.juaraandroid_pokemonapp.core.data.datasource.response

import com.squareup.moshi.Json

/**
 * Created by Ian Damping on 07,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
data class PokemonDetailResponse(
    @Json(name = "id") val pokemonId: Int,
    @Json(name = "name") val pokemonName: String,
    @Json(name = "weight") val pokemonWeight: Int,
    @Json(name = "height") val pokemonHeight: Int,
    @Json(name = "sprites") val pokemonImage: PokemonSpritesResponse,
    @Json(name = "stats") val pokemonStats: List<PokemonBasicStatsResponse>,
    @Json(name = "types") val pokemonTypes: List<PokemonTypesResponse>,
    @Json(name = "abilities") val pokemonAbilities: List<PokemonAbilitiesResponse>,
    @Json(name = "species") val pokemonSpecies: PokemonSpeciesResultResponse,
)
