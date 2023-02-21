package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.area

import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.ApiInterface
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.base.BaseSource
import com.example.juaraandroid_pokemonapp.core.data.datasource.remote.rest.NetworkConstant
import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult
import javax.inject.Inject

class RemotePokemonAreaManagerImpl @Inject constructor(
    baseSource: BaseSource,
    private val api: ApiInterface
) : RemotePokemonAreaManager, BaseSource by baseSource {
    override suspend fun getPokemonLocationAreas(id: Int): ApiResult<List<String>> {
        return when (val response = oneShotCalls(api.getPokemonLocationAreas(id))) {
            is ApiResult.Error -> ApiResult.Error(response.exception)
            is ApiResult.Success -> {
                if (response.data.isNotEmpty()) {
                    ApiResult.Success(response.data.map { it.area.name })
                } else ApiResult.Error(Exception(NetworkConstant.EMPTY_DATA))
            }
        }
    }
}