package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.area

import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult

interface RemotePokemonAreaManager {

    suspend fun getPokemonLocationAreas(id: Int): ApiResult<List<String>>
}