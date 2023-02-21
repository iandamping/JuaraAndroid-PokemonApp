package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.eggGroup

import com.example.juaraandroid_pokemonapp.core.data.datasource.response.ItemPokemonEggResponse

interface RemotePokemonEggGroupManager {

    @kotlin.jvm.Throws(Exception::class)
    suspend fun getPokemonEggGroup(url: String): List<ItemPokemonEggResponse>
}