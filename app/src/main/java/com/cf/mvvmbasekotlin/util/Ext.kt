package com.cf.mvvmbasekotlin.util

import android.app.Activity
import com.cf.mvvmbasekotlin.model.api.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import luyao.util.ktx.ext.toast
import retrofit2.HttpException
import java.net.HttpCookie

const val TOOL_URL = "http://www.wanandroid.com/tools"
const val GITHUB_PAGE = "https://github.com/lulululbj/wanandroid"
const val ISSUE_URL = "https://github.com/lulululbj/wanandroid/issues"

suspend fun executeResponse(
    response: Response<Any>, successBlock: suspend CoroutineScope.() -> Unit,
    errorBlock: suspend CoroutineScope.() -> Unit
) {
    coroutineScope {
        if (response.errorCode == -1) errorBlock
        else successBlock
    }
}

fun Activity.onNetError(e: Throwable, func: (e: Throwable) -> Unit) {
    if (e is HttpException) {
        toast("网络异常!")
        func(e)
    }
}

fun Response<Any>.isSuccess(): Boolean = this.errorCode == 0
