package com.cf.ktx_base.core.lifecycle

import android.app.Activity
import java.util.*

object KtxManager {
    private val mActivityList = LinkedList<Activity>()
    val currentActivity: Activity?
        get() =
            if (mActivityList.isEmpty()) null
            else mActivityList.last  //last表示最近加入的集合

    /**
     *@作者:陈飞
     *@说明:将Activity加入集合
     *@时间:2019/11/27 11:08
     */
    fun pushActivity(activity: Activity) {
        if (mActivityList.contains(activity)) {//如果存在，那么先移除再加入,否则直接加入
            if (mActivityList.last != activity) {
                mActivityList.remove(activity)
                mActivityList.add(activity)
            }
        } else {
            mActivityList.add(activity)
        }
    }

    /**
     *@作者:陈飞
     *@说明:移除Activity
     *@时间:2019/11/27 11:09
     */
    fun popActivity(activity: Activity) {
        mActivityList.remove(activity)
    }

    /**
     *@作者:陈飞
     *@说明:结束当前的Activity
     *@时间:2019/11/27 11:12
     */
    fun finishCurrentActivity() {
        currentActivity?.finish()
    }

    /**
     *@作者:陈飞
     *@说明:结束指定的Activity
     *@时间:2019/11/27 11:12
     */
    fun finishActivity(activity: Activity) {
        mActivityList.remove(activity)
        activity.finish()
    }

    /**
     *@作者:陈飞
     *@说明:结束所有的Activity
     *@时间:2019/11/27 11:13
     */
    fun finishAllActivity() {
        for (activity in mActivityList)
            activity.finish()
    }

}