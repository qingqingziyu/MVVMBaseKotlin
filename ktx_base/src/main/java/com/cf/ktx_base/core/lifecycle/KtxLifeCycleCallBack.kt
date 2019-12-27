package com.cf.ktx_base.core.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.cf.ktx_base.ext.loge

class KtxLifeCycleCallBack : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(p0: Activity) {

    }

    override fun onActivityStarted(p0: Activity) {
        "onActivityStarted : ${p0.localClassName}".loge()
    }

    override fun onActivityDestroyed(p0: Activity) {
        "onActivityDestroyed : ${p0.localClassName}".loge()
        KtxManager.popActivity(p0)
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityStopped(p0: Activity) {
        "onActivityStopped : ${p0.localClassName}".loge()
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        KtxManager.pushActivity(p0)
        "onActivityCreated : ${p0.localClassName}".loge()
    }

    override fun onActivityResumed(p0: Activity) {
        "onActivityResumed : ${p0.localClassName}".loge()
    }

}