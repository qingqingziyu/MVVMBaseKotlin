package com.cf.mvvmbasekotlin

import android.app.Application
import android.content.Context
import com.cf.mvvmbasekotlin.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import kotlin.properties.Delegates

/**
 *@作者:陈飞
 *@说明:程序入口初始化
 *@时间:2019/12/26 16:14
 */
class App : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()//委托模式，非空值强校验
    }

    override fun onCreate() {
        super.onCreate()

        CONTEXT = applicationContext

        //创建koinApplication 的实例配置
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }


    }
}