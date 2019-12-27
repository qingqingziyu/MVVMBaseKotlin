package com.cf.ktx_base.core.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.cf.ktx_base.Ktx
import luyao.util.ktx.ext.toast
/**
 *@作者:陈飞
 *@说明:监听整个应用生命周期
 *@时间:2019/11/27 10:49
 */
class KtxAppLifeObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        Ktx.app.toast("应用进入前台")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        Ktx.app.toast("应用进入后台")
    }
}