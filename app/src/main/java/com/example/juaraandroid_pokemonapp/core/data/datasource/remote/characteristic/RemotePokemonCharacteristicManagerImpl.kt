package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.characteristic

import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.ApiInterface
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.base.BaseSource
import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult
import javax.inject.Inject

class RemotePokemonCharacteristicManagerImpl @Inject constructor(
    baseSource: BaseSource,
    private val api: ApiInterface
) : RemotePokemonCharacteristicManager, BaseSource by baseSource {
    override suspend fun getDetailPokemonCharacteristic(id: Int): ApiResult<String> {
        return when (val response = oneShotCalls(api.getPokemonCharacteristic(id))) {
            is ApiResult.Error -> ApiResult.Error(response.exception)
            is ApiResult.Success -> {
                val position = response.data.descriptions.indexOfFirst { it.language.name == "en" }
                ApiResult.Success(response.data.descriptions[position].description)
            }
        }
    }
}