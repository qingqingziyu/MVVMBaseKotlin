package com.cf.ktx_base.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.lang.ClassCastException

/**
 *@作者:陈飞
 *@说明:基类的ViewModel
 *@时间:2019/11/27 10:34
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {
    //open 关键字，可以继承该类
    val mException: MutableLiveData<Throwable> =
        MutableLiveData() //负责数据暂存，是一个观察者模式的数据实体类，他可以跟它注册的观察者灰树咀数据更新

    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    suspend fun <T> launchOnIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }

    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, true)
        }
    }

    fun launchOnUITryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean
    ) {
        launchOnUI {
            tryCatch(tryBlock, catchBlock, finallyBlock, handleCancellationExceptionManually)
        }
    }

    fun launchOnUITryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, handleCancellationExceptionManually)
        }
    }

    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Throwable) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    mException.value = e
                    catchBlock(e)
                }
            }
        }
    }


}