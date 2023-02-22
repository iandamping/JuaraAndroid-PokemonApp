package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.characteristic

import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult

interface RemotePokemonCharacteristicManager {

    suspend fun getDetailPokemonCharacteristic(id: Int): ApiResult<String>
}