package com.example.juaraandroid_pokemonapp.core.data.datasource.response

import com.squareup.moshi.Json


/**
 * Created by Ian Damping on 10,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */

data class PokemonSpeciesDetailResponse(
    @Json(name = "base_happiness") val pokemonHappines: Int,
    @Json(name = "capture_rate") val pokemonCaptureRate: Int,
    @Json(name = "color") val pokemonColor: PokemonSpeciesColorResponse,
    @Json(name = "egg_groups") val pokemonEggGroup: List<PokemonSpeciesEggGroupResponse>,
    @Json(name = "generation") val pokemonGeneration: PokemonGenerationResponse,
    @Json(name = "growth_rate") val pokemonGrowthRate: PokemonGrowthRateResponse,
    @Json(name = "habitat") val pokemonHabitat: PokemonHabitatResponse,
    @Json(name = "shape") val pokemonShape: PokemonShapeResponse,
)

data class PokemonGenerationResponse(@Json(name = "name") val pokemonGenerationLString: String)

data class PokemonGrowthRateResponse(@Json(name = "name") val pokemonGrowthRate: String)

data class PokemonHabitatResponse(@Json(name = "name") val pokemonHabitat: String)

data class PokemonShapeResponse(@Json(name = "name") val pokemonShape: String)

data class PokemonSpeciesColorResponse(@Json(name = "name") val pokemonColor: String)

data class PokemonSpeciesEggGroupResponse(@Json(name = "name") val eggName: String)