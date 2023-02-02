package com.example.juaraandroid_pokemonapp.core.data.datasource.remote

import com.example.juaraandroid_pokemonapp.core.domain.common.ApiResult
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class BaseSourceImpl @Inject constructor() : BaseSource {
    override suspend fun <T> oneShotCalls(call: Response<T>): ApiResult<T> {
        return try {
            if (call.isSuccessful) {
                val body = call.body()
                if (body != null) {
                    ApiResult.Success(body)
                } else {
                    ApiResult.Error(Exception(NetworkConstant.RESPONSE_BODY_NULL))
                }
            } else ApiResult.Error(Exception(NetworkConstant.SERVER_ERROR))
        } catch (e: Exception) {
            ApiResult.Error(e)
        } catch (e: IOException) {
            ApiResult.Error(e)
        } catch (e: SocketTimeoutException) {
            ApiResult.Error(e)
        }
    }
}