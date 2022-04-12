package com.example.juaraandroid_pokemonapp.core.data.datasource.remote

import com.example.juaraandroid_pokemonapp.core.data.model.ApiResult
import retrofit2.Response

/**
 * Created by Ian Damping on 07,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface BaseSource {
    suspend fun <T> oneShotCalls(call: suspend () -> Response<T>): ApiResult<T>
}