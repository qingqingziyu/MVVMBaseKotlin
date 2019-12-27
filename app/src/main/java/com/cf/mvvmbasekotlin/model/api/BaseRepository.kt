package com.cf.mvvmbasekotlin.model.api

import com.cf.mvvmbasekotlin.core.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import java.lang.Exception

/**
 *@作者:陈飞
 *@说明:请求数据体基类
 *@时间:2019/12/26 15:31
 */
open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Response<T> {
        return call.invoke()
    }


    /**
     *@作者:陈飞
     *@说明:请求网络
     *@时间:2019/12/26 15:54
     */
    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Result<T>, //回调
        errorMessage: String //错误码
    ): Result<T> {
        return try {
            call()
        } catch (e: Exception) {
            Result.Error(IOException(errorMessage, e))
        }
    }

    suspend fun <T : Any> executeResponse(
        response: Response<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): Result<T> {
        return coroutineScope {
            if (response.errorCode == -1) {
                errorBlock?.let { it() }
                Result.Error(IOException(response.errorMsg))
            } else {
                successBlock?.let { it() }
                Result.Success(response.data)
            }
        }
    }
}