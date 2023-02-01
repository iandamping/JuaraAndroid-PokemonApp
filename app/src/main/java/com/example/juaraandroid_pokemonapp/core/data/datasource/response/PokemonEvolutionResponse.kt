package com.example.juaraandroid_pokemonapp.core.data.datasource.response

import com.squareup.moshi.Json

data class PokemonEvolutionResponse(@Json(name = "chain") val evolutionChain: ItemPokemonEvolutionChainResponse)

data class ItemPokemonEvolutionChainResponse(@Json(name = "evolves_to") val evolveTo: List<ItemEvolveToResponse>)

data class ItemEvolveToResponse(@Json(name = "species") val evolvingPokemonSpecies: EvolvingPokemon)

data class EvolvingPokemon(@Json(name = "name") val eggName: String, @Json(name = "url") val url: String)
