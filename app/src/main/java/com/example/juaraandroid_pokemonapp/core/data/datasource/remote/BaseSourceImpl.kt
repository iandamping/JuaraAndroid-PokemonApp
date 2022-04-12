package com.example.juaraandroid_pokemonapp.core.data.datasource.remote

import com.example.juaraandroid_pokemonapp.core.data.model.ApiResult
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class BaseSourceImpl @Inject constructor(): BaseSource {
    override suspend fun <T> oneShotCalls(call: suspend () -> Response<T>): ApiResult<T> {
        try {
            val response = call.invoke()
            return if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiResult.Success(body)
                } else {
                    ApiResult.Error(Exception("body is null"))
                }
            } else return ApiResult.Error(Exception("response not success"))
        } catch (e: Exception) {
            return when (e) {
                is IOException -> {
                    ApiResult.Error(e)
                }
                is SocketTimeoutException -> {
                    ApiResult.Error(e)
                }
                else -> {
                    ApiResult.Error(e)
                }
            }
        }
    }
}