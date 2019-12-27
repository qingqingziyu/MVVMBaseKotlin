package com.cf.ktx_base.core.lifecycle

import android.os.Handler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.util.*
import java.util.concurrent.Callable
import javax.security.auth.callback.Callback

/**
 *@作者:陈飞
 *@说明:绑定生命周期的handle
 *@时间:2019/11/27 10:57
 */
class KtxHandler(lifecycleObserver: LifecycleOwner, callback: Callback) : Handler(callback),
    LifecycleObserver {

    private val mLifecycleOwner: LifecycleOwner = lifecycleObserver

    /**
     *@作者:陈飞
     *@说明:类初始化运行
     *@时间:2019/11/27 10:52
     */
    init {
        //加入生命周期监听
        lifecycleObserver.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestory() {
        removeCallbacksAndMessages(null)
        mLifecycleOwner.lifecycle.removeObserver(this)
    }

}