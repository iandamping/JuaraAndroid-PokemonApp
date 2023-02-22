package com.example.juaraandroid_pokemonapp.core.data.datasource.remote.base

import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult
import retrofit2.Response

/**
 * Created by Ian Damping on 07,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface BaseSource {
    suspend fun <T> oneShotCalls(call: Response<T>): ApiResult<T>
}