package com.example.juaraandroid_pokemonapp.core.data.datasource.remote

/**
 * Created by Ian Damping on 07,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
object NetworkConstant {
    const val cacheSize = 10L * 1024 * 1024 // 10MB
    const val BASE_URL = "https://pokeapi.co/api/v2/"
    const val GET_POKEMON = "pokemon"
    const val GET_POKEMON_CHARACTERISTIC = "characteristic"
    const val GET_POKEMON_AREAS = "encounters"
    const val EMPTY_DATA = "EMPTY"
    const val NETWORK_ERROR = "NETWORK ERROR"
    const val POKEMON_STARTING_OFFSET = 0
    const val POKEMON_OFFSET = 20
    const val NETWORK_PAGE_SIZE = 2
}